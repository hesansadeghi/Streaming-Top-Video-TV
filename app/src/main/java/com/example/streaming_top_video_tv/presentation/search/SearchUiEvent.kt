package com.example.streaming_top_video_tv.presentation.search

sealed class SearchUiEvent {

    data class SearchMoviesByQuery(val q: String):SearchUiEvent()

    data class ClickedItemMovie(val movieId: Int?) : SearchUiEvent()

    data object BackStack : SearchUiEvent()

}