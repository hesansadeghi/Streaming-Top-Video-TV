package com.example.streaming_top_video_tv.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.streaming_top_video_tv.data.models.ResponseDetaile
import com.example.streaming_top_video_tv.util.Constants

@Entity(tableName = Constants.FAVORITE_MOVIE_TABLE)
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = false)
    var movieId: Int = 0,
    var responseDetaile: ResponseDetaile,
)
