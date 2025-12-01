package com.example.streaming_top_video_tv.presentation.downloads

import com.example.streaming_top_video_tv.data.db.entities.DownloadedMovieEntity

data class DownloadsUiState(
    val downloadedMovies: MutableList<DownloadedMovieEntity> = mutableListOf()
)