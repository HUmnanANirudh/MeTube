package com.example.first.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextDescription(heading:String,description:String,onEnterClick: () -> Unit){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = heading,
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = description,
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.White),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        FilledButton(name = "Get started", onEnterClick = onEnterClick)
    }
}