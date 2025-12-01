package com.example.streaming_top_video_tv.data.repositories

import com.example.streaming_top_video_tv.data.api.ApiService
import com.example.streaming_top_video_tv.data.models.ResponseMoviesList
import com.example.streaming_top_video_tv.data.repositories.interfaces.GetsSearchMoviesByQueryRepository
import com.example.streaming_top_video_tv.util.Resource
import com.example.streaming_top_video_tv.util.safeApiCall
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: ApiService
) : GetsSearchMoviesByQueryRepository {

    override suspend fun getsSearchMoviesByQueryRepository(q: String): Resource<ResponseMoviesList> {
        return safeApiCall { api.searchMovies(q) }
    }
}