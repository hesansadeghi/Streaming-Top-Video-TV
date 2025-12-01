package com.example.streaming_top_video_tv.presentation.detaile

import com.example.streaming_top_video_tv.data.models.ResponseDetaile

data class DetaileUiState(
    val detaile:ResponseDetaile? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val isFavorite: Boolean = false,
    val popBackStack: Boolean = false
)