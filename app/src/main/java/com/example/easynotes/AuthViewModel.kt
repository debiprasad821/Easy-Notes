package com.example.easynotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easynotes.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private var _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.signUp(name, email, password)
            _authState.value = result
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.signIn(email, password)
            _authState.value = result
        }
    }
}