package com.example.streaming_top_video_tv.data.uses_cases.detaile

import com.example.streaming_top_video_tv.data.db.entities.FavoriteMovieEntity
import com.example.streaming_top_video_tv.data.repositories.DetaileRepository
import jakarta.inject.Inject

class DeleteFavoriteMovieCase @Inject constructor(private val repository: DetaileRepository) {

    suspend operator fun invoke(entity: FavoriteMovieEntity) =
        repository.deleteFavoriteMovie(entity)
}