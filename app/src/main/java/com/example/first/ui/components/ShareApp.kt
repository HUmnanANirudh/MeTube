package com.example.first.ui.components

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShareApp(
    onSwitchtoPhone: () -> Unit,
    onSignIn: () -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Show Us Some Love",
                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(text = "Want to SignIn ? ", color = Color.White,)
                Text(
                    text = "Click here",
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic),
                    modifier = Modifier.clickable { onSwitchtoPhone() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter Email", textAlign = TextAlign.Center) },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(40.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    if(email.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "message/rfc822"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                            putExtra(Intent.EXTRA_SUBJECT, "You’ve got to check this out!")
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Hey!\n\nMeTube is awesome!Super fun and easy to use.\n\nCheers!"
                            )
                        }
                        context.startActivity(Intent.createChooser(intent, "Send Email"))
                        Toast.makeText(context,"Lots of Love",Toast.LENGTH_SHORT).show()
                        onSignIn()
                    }else{
                        Toast.makeText(context,"Enter an email",Toast.LENGTH_SHORT).show()
                    }
                          }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(red = 191, green = 64, blue = 191, alpha = 255),
                    contentColor = Color.White),
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,

            ) {
                Text(text = "Share our mail")
            }
        }
    }
}