package com.example.streaming_top_video_tv.util

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.navigation.NavHostController
import com.example.streaming_top_video_tv.models.DownloadUiModel
import java.util.Locale


object Utils {

    fun formatMsToString(timeInMs: Long): String {

        var secondsRemaining = timeInMs / 1000
        val hours = secondsRemaining / 3600
        secondsRemaining -= hours * 3600
        val minutes = secondsRemaining / 60
        secondsRemaining -= minutes * 60
        val seconds = secondsRemaining

        return String.Companion.format(
            Locale.getDefault(),
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds
        )
    }


    @OptIn(UnstableApi::class)
    fun sendEventBus(context: Context, download: Download) {

        Log.e(
            "download",
            "sendDownloadBroadcast->  id: ${download.request.id}" +
                    " state: ${download.state} " +
                    "percent: ${download.percentDownloaded} " +
                    " bytesDownloaded: ${download.bytesDownloaded}"
        )

//        val intent = Intent(DOWNLOAD_UPDATE).apply {
//            putExtra(REQUEST_ID, download.request.id)
//            putExtra(DOWNLOAD_STATE, download.state)
//            putExtra(DOWNLOAD_PERCENT, download.percentDownloaded)
//            putExtra(DOWNLOAD_BYTE, download.bytesDownloaded)
//        }
//        context.sendBroadcast(intent)


//        val intent = Intent(context, DownloadUpdateReceiver::class.java).apply {
//            action = DOWNLOAD_UPDATE
//            putExtra(REQUEST_ID, download.request.id)
//            putExtra(DOWNLOAD_STATE, download.state)
//            putExtra(DOWNLOAD_PERCENT, download.percentDownloaded)
//            putExtra(DOWNLOAD_BYTE, download.bytesDownloaded)
//        }
//
//        context.sendBroadcast(intent)


        DownloadEventBus.send(
            DownloadUiModel(
                download.request.id,
                download.state,
                download.percentDownloaded,
                download.bytesDownloaded
            )
        )

    }

//    @Composable
//    fun SetStatusBarColor(darkIcons: Boolean) {
//        val view = LocalView.current
//        val window = (view.context as ComponentActivity).window
//
//
//        WindowCompat.getInsetsController(window, view).apply {
//            isAppearanceLightStatusBars = darkIcons
//        }
//
//    }


    fun popBackstack(activity: Activity, navController: NavHostController) {

        navController.popBackStack()
    }


    fun formatFileSize(bytes: Long): String {
        if (bytes <= 0) return "0 B"

        val kb = 1024.0
        val mb = kb * 1024
        val gb = mb * 1024

        return when {
            bytes >= gb -> String.format(Locale.getDefault(), "%.2f GB", bytes / gb)
            bytes >= mb -> String.format(Locale.getDefault(), "%.2f MB", bytes / mb)
            bytes >= kb -> String.format(Locale.getDefault(), "%.2f KB", bytes / kb)
            else -> "$bytes B"
        }
    }
}

