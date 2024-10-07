package com.example.easynotes.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.easynotes.data.model.Note
import com.example.easynotes.screens.*
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.N)
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

        composable(
            route = "add_note?note={note}",
            arguments = listOf(
                navArgument("note") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val noteJson = backStackEntry.arguments?.getString("note")
            val note = Gson().fromJson(noteJson, Note::class.java)
            AddNoteScreen(navController, note)
        }
    }
}