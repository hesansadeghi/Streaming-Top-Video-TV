package com.example.streaming_top_video_tv.models

data class DownloadUiModel(
    val id: String,
    val state: Int,
    val percentDownloaded: Float,
    val bytesDownloaded: Long
)
