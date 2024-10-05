package com.example.easynotes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.easynotes.AuthViewModel
import com.example.easynotes.R
import com.example.easynotes.ui.theme.Teal500
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState(initial = false)
    LaunchedEffect(key1 = Unit) {
        delay(2000)
        if (isLoggedIn) {
            navController.navigate("home") {
                popUpTo("splash") {
                    inclusive = true
                }
            }
        } else {
            navController.navigate("signIn") {
                popUpTo("splash") {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_notes),
                contentDescription = null,
            )

            Text(
                text = "EASY NOTES",
                modifier = Modifier
                    .padding(top = 15.dp),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold_italic)),
                color = Teal500,
                letterSpacing = 2.sp
            )
        }
    }
}

@Composable
@Preview(device = Devices.PIXEL)
fun SplashScreenPreview() {
//    SplashScreen()
}