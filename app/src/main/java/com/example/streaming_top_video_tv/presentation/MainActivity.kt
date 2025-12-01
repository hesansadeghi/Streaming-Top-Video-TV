package com.example.streaming_top_video_tv.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.streaming_top_video_tv.presentation.main.MainScreen
import com.example.streaming_top_video_tv.presentation.ui.theme.StreamingTopVideoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            StreamingTopVideoTheme {

                    MainScreen()
            }
        }
    }

}



