package com.example.first.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import com.example.first.R
import com.example.first.ui.components.Header
import java.io.File



@Composable
fun AudioScreen() {
    val context = LocalContext.current

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(context, "Notification permission is needed for playback notification", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    val audioDir = File(context.getExternalFilesDir(null), "MeTube/audio").apply { mkdirs() }

    var isRecording by remember { mutableStateOf(false) }
    var mediaRecorder: MediaRecorder? by remember { mutableStateOf(null) }

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var currentlyPlayingFile by remember { mutableStateOf<File?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    var audioFiles by remember { mutableStateOf(emptyList<File>()) }
    LaunchedEffect(Unit) {
        audioFiles = audioDir.listFiles { _, name ->
            name.endsWith(".mp3") || name.endsWith(".m4a")
        }?.toList() ?: emptyList()
    }

    fun refreshAudioList() {
        audioFiles = audioDir.listFiles { _, name ->
            name.endsWith(".mp3") || name.endsWith(".m4a")
        }?.toList() ?: emptyList()
    }

    fun startRecording() {
        val fileName = "AUD_${System.currentTimeMillis()}.m4a"
        val file = File(audioDir, fileName)
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(file.absolutePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            try {
                prepare()
                start()
                isRecording = true
                Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Recording failed", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    fun stopRecording() {
        try {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = null
            isRecording = false
            Toast.makeText(context, "Recording saved", Toast.LENGTH_SHORT).show()
            refreshAudioList()
        } catch (e: Exception) {
            Toast.makeText(context, "Error stopping recording", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            startRecording()
        } else {
            Toast.makeText(context, "Microphone permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (isRecording) {
                    stopRecording()
                } else {
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }
        ) {
            Text(if (isRecording) "Stop Recording" else "Start Recording")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Audio Files")

        if (audioFiles.isEmpty()) {
            Text("No audio found", modifier = Modifier.padding(top = 10.dp))
        } else {
            audioFiles.forEach { file ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(file.name, modifier = Modifier.weight(1f))

                    Button(onClick = {
                        if (isPlaying && currentlyPlayingFile == file) {
                            mediaPlayer?.pause()
                            isPlaying = false
                        } else {
                            mediaPlayer?.release()
                            val uri: Uri = FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.provider",
                                file
                            )
                            mediaPlayer = MediaPlayer().apply {
                                setDataSource(context, uri)
                                prepare()
                                start()
                                setOnCompletionListener {
                                    isPlaying = false
                                    currentlyPlayingFile = null
                                }
                            }
                            isPlaying = true
                            currentlyPlayingFile = file
                            showNotification(context, file.name)
                        }
                    }) {
                        Text(
                            if (isPlaying && currentlyPlayingFile == file) "Pause"
                            else "Play"
                        )
                    }

                    Button(onClick = {
                        if (isPlaying && currentlyPlayingFile == file) {
                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = null
                            isPlaying = false
                            currentlyPlayingFile = null
                        }
                    }) {
                        Text("Stop")
                    }
                }
            }
        }
    }
}
fun showNotification(context: Context, fileName: String) {
    val channelId = "media_playback_channel"
    val notificationManager = NotificationManagerCompat.from(context)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannelCompat.Builder(channelId, NotificationManagerCompat.IMPORTANCE_LOW)
            .setName("Media Playback")
            .build()
        notificationManager.createNotificationChannel(channel)
    }

    // Runtime check for POST_NOTIFICATIONS permission (Android 13+)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Do NOT try to request here — you can't from a non-Activity context
            Toast.makeText(context, "Notification permission not granted", Toast.LENGTH_SHORT).show()
            return
        }
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle("Now Playing")
        .setContentText(fileName)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setOngoing(true)
        .build()

    notificationManager.notify(1, notification)
}
