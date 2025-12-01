package com.example.streaming_top_video_tv.data.repositories.interfaces

import com.example.streaming_top_video_tv.data.models.ResponseMoviesList
import com.example.streaming_top_video_tv.util.Resource

interface GetsSearchMoviesByQueryRepository {

    suspend fun getsSearchMoviesByQueryRepository(q: String) :Resource<ResponseMoviesList>
}