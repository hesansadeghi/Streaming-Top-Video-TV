package com.example.streaming_top_video_tv.data.repositories.interfaces

interface ExistsFavoriteMovieRepository {

    suspend fun existFavoriteMovie(id: Int) : Boolean
}