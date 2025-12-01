package com.example.streaming_top_video_tv.data.repositories.interfaces

import com.example.streaming_top_video_tv.data.models.ResponseGenresList
import com.example.streaming_top_video_tv.util.Resource

interface GenresRepository {

    suspend fun getGenres() : Resource<ResponseGenresList>
}