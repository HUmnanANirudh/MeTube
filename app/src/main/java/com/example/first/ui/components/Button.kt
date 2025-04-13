package com.example.first.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun FilledButton (name: String,onEnterClick: () -> Unit){
    Button(onClick = onEnterClick , colors = ButtonDefaults.buttonColors(
        containerColor = Color(red = 191, green = 64, blue = 191, alpha = 255),
        contentColor = Color.White
    ),contentPadding = ButtonDefaults.ButtonWithIconContentPadding) {

        Text(
            text = name,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
        )
    }
}