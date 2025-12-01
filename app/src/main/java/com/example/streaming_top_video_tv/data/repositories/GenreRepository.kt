package com.example.streaming_top_video_tv.data.repositories

import com.example.streaming_top_video_tv.data.api.ApiService
import com.example.streaming_top_video_tv.data.models.ResponseMoviesList
import com.example.streaming_top_video_tv.data.repositories.interfaces.MoviesWithGenreRepository
import com.example.streaming_top_video_tv.util.Resource
import com.example.streaming_top_video_tv.util.safeApiCall
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val api: ApiService
) : MoviesWithGenreRepository {

    override suspend fun getMoviesWithGenre(id: Int): Resource<ResponseMoviesList> {

        return safeApiCall { api.getMoviesWithGenre(id) }
    }
}