package com.example.streaming_top_video_tv.data.uses_cases.detaile

import com.example.streaming_top_video_tv.data.db.entities.FavoriteMovieEntity
import com.example.streaming_top_video_tv.data.repositories.DetaileRepository
import jakarta.inject.Inject

class InsertFavoriteMovieCase @Inject constructor(private val repository: DetaileRepository) {

    suspend operator fun invoke(entity: FavoriteMovieEntity) =
        repository.insertFavoriteMovie(entity)
}