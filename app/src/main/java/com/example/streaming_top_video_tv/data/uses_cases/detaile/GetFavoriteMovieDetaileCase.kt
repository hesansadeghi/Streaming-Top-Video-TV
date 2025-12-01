package com.example.streaming_top_video_tv.data.uses_cases.detaile

import com.example.streaming_top_video_tv.data.repositories.DetaileRepository
import jakarta.inject.Inject

class GetFavoriteMovieDetaileCase @Inject constructor(private val repository: DetaileRepository) {

    suspend operator fun invoke(movieId: Int) = repository.getFavoriteMovieDetaile(movieId)
}