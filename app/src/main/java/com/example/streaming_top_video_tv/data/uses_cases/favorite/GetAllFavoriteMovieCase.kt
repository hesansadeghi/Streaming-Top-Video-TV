package com.example.streaming_top_video_tv.data.uses_cases.favorite

import com.example.streaming_top_video_tv.data.repositories.FavoriteRepository
import jakarta.inject.Inject

class GetAllFavoriteMovieCase @Inject constructor(private val repository: FavoriteRepository) {

    suspend operator fun invoke() = repository.getAllFavoriteMovies()
}