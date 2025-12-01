package com.example.streaming_top_video_tv.presentation.favorite

sealed class FavoriteUiEvent {

    data object GetAllFavoriteMovies :FavoriteUiEvent()

    data class ClickedItemMovie(val movieId: Int?) : FavoriteUiEvent()

}