package com.example.easynotes.repository

import com.example.easynotes.AuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {
    override suspend fun signUp(name: String, email: String, password: String): AuthState {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            user?.let {
                val profileChangeRequest =
                    UserProfileChangeRequest.Builder().setDisplayName(name).build()
                it.updateProfile(profileChangeRequest).await()
                it.sendEmailVerification().await()
            }
            AuthState.Success("Sign up successful. Please verify your email.")
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "Sign up failed")
        }
    }

    override suspend fun signIn(email: String, password: String): AuthState {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            AuthState.Authenticated
        } catch (e: Exception) {
            AuthState.Error("Invalid login credential.")
        }
    }

    override suspend fun signOut(): AuthState {
        return try {
            auth.signOut()
            AuthState.Success("Logout success")
        } catch (e: Exception) {
            AuthState.Error(e.message ?: "Logout failed")
        }
    }
}