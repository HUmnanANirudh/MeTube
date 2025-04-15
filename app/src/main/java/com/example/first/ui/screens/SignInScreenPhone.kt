package com.example.first.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.first.ui.components.AppIcon
import com.example.first.ui.components.SignInPhone

@Composable
fun SignInScreenPhone(navController:NavHostController){
    Box(modifier = Modifier.fillMaxSize()) {
       Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(horizontal = 20.dp, vertical = 60.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.SpaceBetween,
       ) {
           AppIcon(modifier = Modifier.align(Alignment.CenterHorizontally))
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(horizontal = 20.dp, vertical = 60.dp),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               SignInPhone(
                   onSwitchToEmail = {
                       navController.navigate("SignInEmail")
                   },
                   onSignIn = {
                       navController.navigate("Home")
                   }
               )
           }
       }

    }
}