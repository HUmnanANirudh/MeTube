package com.example.first.ui.screens

import AppIcon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.first.R
import com.example.first.ui.components.ShareApp

@Composable
fun SignInScreenEmail(navController:NavHostController){
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.share),
            contentDescription = "Share app",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppIcon(modifier = Modifier.paddingFromBaseline(top = 0.dp, bottom = 140.dp))
                ShareApp(
                    onSwitchtoPhone = {
                        navController.navigate("SignInPhone")
                    },
                    onSignIn = {
                        navController.navigate("Home")
                    }
                )
            }

    }
}