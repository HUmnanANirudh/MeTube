package com.example.first

import WelcomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.first.ui.screens.Home
import com.example.first.ui.screens.SignInScreenEmail
import com.example.first.ui.screens.SignInScreenPhone

val Purple = Color(red = 191, green = 64, blue = 191, alpha = 255)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "welcome"){
                composable("welcome") {
                    WelcomeScreen(onEnterClick = {
                        navController.navigate("SignInPhone")
                    })
                }
                composable("SignInPhone") {
                    SignInScreenPhone(navController)
                }
                composable("SignInEmail") {
                    SignInScreenEmail(navController)
                }
                composable("Home") {
                    Home(navController)
                }
            }
        }
    }
}
