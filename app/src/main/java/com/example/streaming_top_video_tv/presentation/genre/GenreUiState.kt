package com.example.streaming_top_video_tv.presentation.genre

import com.example.streaming_top_video_tv.data.models.ResponseMoviesList

data class GenreUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val moviesList: ResponseMoviesList? = null,
    val popBackStack: Boolean = false
)
