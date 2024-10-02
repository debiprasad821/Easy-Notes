package com.example.easynotes.repository

import com.example.easynotes.AuthState

interface AuthRepository {
    suspend fun signUp(name: String, email: String, password: String): AuthState
    suspend fun signIn(email: String, password: String): AuthState
}