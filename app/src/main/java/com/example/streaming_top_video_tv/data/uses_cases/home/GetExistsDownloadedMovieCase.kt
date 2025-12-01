package com.example.streaming_top_video_tv.data.uses_cases.home

import com.example.streaming_top_video_tv.data.repositories.HomeRepository
import jakarta.inject.Inject

class GetExistsDownloadedMovieCase @Inject constructor(private val repository: HomeRepository) {

    suspend operator fun invoke(movieId: String) = repository.existDownloadedMovie(movieId)
}