package com.example.streaming_top_video_tv.data.uses_cases.home

import com.example.streaming_top_video_tv.data.db.entities.DownloadedMovieEntity
import com.example.streaming_top_video_tv.data.repositories.HomeRepository
import jakarta.inject.Inject

class InsertDownloadedMovieCase @Inject constructor(private val repository: HomeRepository) {

    suspend operator fun invoke(entity: DownloadedMovieEntity) = repository.insertDownloadedMovie(entity)
}