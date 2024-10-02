package com.example.easynotes

sealed class AuthState {
    object Authenticated : AuthState()
    data class Success(val message: String) : AuthState()
    object Loading : AuthState()
    data class Error(val error: String) : AuthState()
    object Idle : AuthState()
}
