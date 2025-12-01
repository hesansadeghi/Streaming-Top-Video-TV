package com.example.streaming_top_video_tv.presentation.favorite

import com.example.streaming_top_video_tv.data.db.entities.FavoriteMovieEntity

data class FavoriteUiState(
    val favoriteMovieEntitiesList: MutableList<FavoriteMovieEntity> = mutableListOf()
)
