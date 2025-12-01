package com.example.streaming_top_video_tv.data.repositories.interfaces

import com.example.streaming_top_video_tv.data.models.ResponseMoviesList
import com.example.streaming_top_video_tv.util.Resource

interface LastMoviesRepository {

    suspend fun lastMovies() : Resource<ResponseMoviesList>
}