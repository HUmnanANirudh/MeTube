package com.example.first.ui.components

import android.telephony.SmsManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SignInPhone(
    onSwitchtoEmail: () -> Unit,
    onSignIn: () -> Unit
) {
    val context = LocalContext.current
    val smsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "SMS permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var isOtpSent by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Sign In",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(text = "Want to use Email? ")
                Text(
                    text = "Click here",
                    color = Color.Blue,
                    modifier = Modifier.clickable { onSwitchtoEmail() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Enter Phone Number", textAlign = TextAlign.Center) },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(40.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (isOtpSent) {
                TextField(
                    value = otp,
                    onValueChange = { otp = it },
                    label = { Text("Enter OTP") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(40.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = {
                    if (!isOtpSent) {
                        smsLauncher.launch(Manifest.permission.SEND_SMS)
                        val smsManager = SmsManager.getDefault()
                        smsManager.sendTextMessage(
                            phoneNumber, null,
                            "Your OTP is 5195", null, null
                        )

                        Toast.makeText(context, "OTP sent to $phoneNumber", Toast.LENGTH_SHORT).show()
                        isOtpSent = true
                    } else {
                        if (otp == "5195") {
                            Toast.makeText(context, "OTP Verified!", Toast.LENGTH_SHORT).show()
                            onSignIn()
                        } else {
                            Toast.makeText(context, "Incorrect OTP", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(red = 191, green = 64, blue = 191, alpha = 255),
                    contentColor = Color.White),
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Text(text = if (isOtpSent) "Sign In" else "Get OTP")
            }
        }
    }
}

