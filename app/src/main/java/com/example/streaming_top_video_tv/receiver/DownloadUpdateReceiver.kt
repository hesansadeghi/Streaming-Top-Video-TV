package com.example.streaming_top_video_tv.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import com.example.streaming_top_video_tv.models.DownloadUiModel
import com.example.streaming_top_video_tv.util.Constants.DOWNLOAD_BYTE
import com.example.streaming_top_video_tv.util.Constants.DOWNLOAD_PERCENT
import com.example.streaming_top_video_tv.util.Constants.DOWNLOAD_STATE
import com.example.streaming_top_video_tv.util.Constants.DOWNLOAD_UPDATE
import com.example.streaming_top_video_tv.util.Constants.REQUEST_ID

class DownloadUpdateReceiver(private val downloads: (DownloadUiModel) -> Unit) : BroadcastReceiver() {
    @OptIn(UnstableApi::class)
    override fun onReceive(p0: Context?, intent: Intent?) {
        if (intent?.action == DOWNLOAD_UPDATE) {
            val id = intent.getStringExtra(REQUEST_ID) ?: return
            val state = intent.getIntExtra(DOWNLOAD_STATE, Download.STATE_STOPPED)
            val percentDownloaded = intent.getFloatExtra(DOWNLOAD_PERCENT, 0f)
            val byteDownloaded = intent.getLongExtra(DOWNLOAD_BYTE, 0L)

            Log.e(
                "downloadUpdateReceiver",
                "Receiver DownloadBroadcast->  id: ${id}" +
                        " state: ${state} " +
                        "percent: ${percentDownloaded} " +
                        " bytesDownloaded: ${byteDownloaded}"
            )

            downloads(DownloadUiModel(id,state,percentDownloaded,byteDownloaded))

        }
    }


}