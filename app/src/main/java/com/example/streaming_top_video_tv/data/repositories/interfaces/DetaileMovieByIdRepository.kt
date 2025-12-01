package com.example.streaming_top_video_tv.data.repositories.interfaces

import com.example.streaming_top_video_tv.data.models.ResponseDetaile
import com.example.streaming_top_video_tv.util.Resource

interface DetaileMovieByIdRepository {

    suspend fun detaileMovie(movieId: Int): Resource<ResponseDetaile>
}