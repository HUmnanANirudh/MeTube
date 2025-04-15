package com.example.first.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.first.R
import com.example.first.ui.components.Header
import com.example.first.ui.components.NavItems
import android.Manifest
import androidx.compose.runtime.mutableIntStateOf
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun Home() {
    val navItemsList = listOf(
        NavItems(label = "Home", icon = painterResource(id = R.drawable.house)),
        NavItems(label = "Audio", icon = painterResource(id = R.drawable.music)),
        NavItems(label = "Camera", icon = painterResource(id = R.drawable.camera)),
        NavItems(label = "Video", icon = painterResource(id = R.drawable.video))
    )

    val context = LocalContext.current
    var selectedIndex by remember { mutableIntStateOf(0) }

    val photoUri = remember { mutableStateOf<Uri?>(null) }

    val visualDir = File(context.getExternalFilesDir(null), "MeTube/visual").apply { mkdirs() }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            Toast.makeText(context, "Image saved to visual folder", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Image capture cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            photoUri.value?.let { uri ->
                cameraLauncher.launch(uri)
            }
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemsList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            if (index == 2 ) {
                                val file = File(visualDir, "IMG_${System.currentTimeMillis()}.jpg")
                                val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                                photoUri.value = uri
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            } else {
                                selectedIndex = index
                            }
                        },
                        icon = {
                            Icon(
                                painter = navItem.icon,
                                contentDescription = navItem.label,
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                    when (selectedIndex) {
                        0-> HomeScreen()
                        1 -> AudioScreen()
                        3 -> VideoScreen()
                    }
                }
            }
    }