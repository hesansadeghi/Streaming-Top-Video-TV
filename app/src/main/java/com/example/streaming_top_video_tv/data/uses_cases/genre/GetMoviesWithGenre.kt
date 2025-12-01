package com.example.streaming_top_video_tv.data.uses_cases.genre

import com.example.streaming_top_video_tv.data.repositories.GenreRepository
import jakarta.inject.Inject

class GetMoviesWithGenre @Inject constructor(private val repository: GenreRepository) {

    suspend operator fun invoke(id: Int) = repository.getMoviesWithGenre(id)
}