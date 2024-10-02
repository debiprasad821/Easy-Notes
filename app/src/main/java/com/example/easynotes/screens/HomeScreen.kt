package com.example.easynotes.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen() {
    val firebaseAuth = FirebaseAuth.getInstance()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "WELCOME TO HOME SCREEN")
        firebaseAuth.currentUser?.let {
            val displayName = it.displayName
            if (displayName != null) {
                Text(text = displayName)
            }
        }
    }
}

@Composable
@Preview(device = Devices.PIXEL)
fun HomeScreenPreview() {
    HomeScreen()
}