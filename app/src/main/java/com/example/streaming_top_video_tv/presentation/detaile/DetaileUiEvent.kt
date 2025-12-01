package com.example.streaming_top_video_tv.presentation.detaile

import com.example.streaming_top_video_tv.data.db.entities.FavoriteMovieEntity

sealed class DetaileUiEvent {

    data class GetDetaileMovieById(val movieId: Int) : DetaileUiEvent()
    data class InsertFavorite(val entity: FavoriteMovieEntity) : DetaileUiEvent()
    data class DeleteFavorite(val entity: FavoriteMovieEntity) : DetaileUiEvent()
    data object BackStack : DetaileUiEvent()

}