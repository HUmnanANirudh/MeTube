package com.example.first.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.first.R
import com.example.first.ui.components.AppIcon
import com.example.first.ui.components.TextDescription

@Composable
fun WelcomeScreen (onEnterClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 60.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AppIcon(modifier = Modifier.align(Alignment.CenterHorizontally))
            TextDescription(heading = "Stream Anything, Anytime", description = "Welcome to MeTube — your personalized video & audio streaming hub.\n" + "Enjoy unlimited content, anywhere, anytime.",onEnterClick)
        }
    }
}