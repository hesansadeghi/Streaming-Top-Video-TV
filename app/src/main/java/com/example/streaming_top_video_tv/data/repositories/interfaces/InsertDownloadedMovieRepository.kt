package com.example.streaming_top_video_tv.data.repositories.interfaces

import com.example.streaming_top_video_tv.data.db.entities.DownloadedMovieEntity

interface InsertDownloadedMovieRepository {

    suspend fun insertDownloadedMovie(entity: DownloadedMovieEntity)
}