package com.example.streaming_top_video_tv.data.repositories.interfaces

interface ExistsDownloadedMovieRepository {

    suspend fun existDownloadedMovie(id: String) : Boolean
}