package com.example.easynotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easynotes.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }

        composable("signIn") {
            SignInScreen(navController)
        }

        composable("signUp") {
            SignUpScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("add_note") {
            AddNoteScreen(navController)
        }
    }
}