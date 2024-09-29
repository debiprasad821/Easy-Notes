package com.example.easynotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easynotes.screens.HomeScreen
import com.example.easynotes.screens.SignInScreen
import com.example.easynotes.screens.SignUpScreen
import com.example.easynotes.screens.SplashScreen

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
            HomeScreen()
        }
    }
}