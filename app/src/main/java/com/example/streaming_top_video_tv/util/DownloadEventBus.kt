package com.example.streaming_top_video_tv.util

import com.example.streaming_top_video_tv.models.DownloadUiModel

object DownloadEventBus {
    var listener: ((DownloadUiModel) -> Unit)? = null

    fun send(download: DownloadUiModel) {
        listener?.invoke(download)
    }
}