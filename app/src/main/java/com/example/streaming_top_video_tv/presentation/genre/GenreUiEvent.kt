package com.example.streaming_top_video_tv.presentation.genre

sealed class GenreUiEvent {

    data class GetMoviesByGenre(val genreId: Int) : GenreUiEvent()

    data class ClickedItemMovie(val movieId: Int?) : GenreUiEvent()

    data object BackStack : GenreUiEvent()

}