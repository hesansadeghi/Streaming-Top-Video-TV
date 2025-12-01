package com.example.streaming_top_video_tv.presentation.search

import com.example.streaming_top_video_tv.data.models.ResponseMoviesList

data class SearchUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val moviesList: ResponseMoviesList? = null,
    val popBackStack: Boolean = false
)
