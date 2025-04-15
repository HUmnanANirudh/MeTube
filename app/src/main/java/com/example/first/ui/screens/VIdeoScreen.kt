package com.example.first.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.first.R
import com.example.first.ui.components.Header
import java.io.File

@Composable
fun VideoScreen() {
    val context = LocalContext.current
    val visualDir = File(context.getExternalFilesDir(null), "MeTube/visual")
    val videoFiles = remember { visualDir.listFiles { _, name -> name.endsWith(".mp4") }?.toList() ?: emptyList() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Available Videos",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        if (videoFiles.isEmpty()) {
            Text("No videos found", fontWeight = FontWeight.Bold)
        } else {
            videoFiles.forEach { file ->
                Text(
                    text = file.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            playVideo(context, file)
                        },
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
private fun playVideo(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "video/*")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No app found to play video", Toast.LENGTH_SHORT).show()
    }
}