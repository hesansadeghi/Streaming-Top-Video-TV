package com.example.streaming_top_video_tv.data.repositories

import com.example.streaming_top_video_tv.data.db.dao.FavoriteMoviesDao
import com.example.streaming_top_video_tv.data.db.entities.FavoriteMovieEntity
import com.example.streaming_top_video_tv.data.repositories.interfaces.GetAllFavoriteMoviesRepository
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao: FavoriteMoviesDao) :
    GetAllFavoriteMoviesRepository {


    override suspend fun getAllFavoriteMovies(): MutableList<FavoriteMovieEntity> {

        return dao.getAllFavoriteMovies()
    }
}