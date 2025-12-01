package com.example.streaming_top_video_tv.presentation.downloads

import com.example.streaming_top_video_tv.data.db.entities.DownloadedMovieEntity


sealed class DownloadsUiEvent{


    object GetAllData :DownloadsUiEvent()

    data class DeleteDownloadedMovie(val entity: DownloadedMovieEntity) : DownloadsUiEvent()


    data class PlayMovie(
        val url: String?,
        val id: String?,
        val titleMovie: String?
    ) : DownloadsUiEvent()
}