package com.example.streaming_top_video_tv.data.repositories

import com.example.streaming_top_video_tv.data.api.ApiService
import com.example.streaming_top_video_tv.data.db.dao.FavoriteMoviesDao
import com.example.streaming_top_video_tv.data.db.entities.FavoriteMovieEntity
import com.example.streaming_top_video_tv.data.models.ResponseDetaile
import com.example.streaming_top_video_tv.data.repositories.interfaces.DeleteFavoriteMovieRepository
import com.example.streaming_top_video_tv.data.repositories.interfaces.DetaileMovieByIdRepository
import com.example.streaming_top_video_tv.data.repositories.interfaces.ExistsFavoriteMovieRepository
import com.example.streaming_top_video_tv.data.repositories.interfaces.GetFavoriteMovieDetaileRepository
import com.example.streaming_top_video_tv.data.repositories.interfaces.InsertFavoriteMovieRepository
import com.example.streaming_top_video_tv.util.Resource
import com.example.streaming_top_video_tv.util.safeApiCall
import javax.inject.Inject

class DetaileRepository @Inject constructor(
    private val api: ApiService,
    private val dao: FavoriteMoviesDao
) : DetaileMovieByIdRepository, ExistsFavoriteMovieRepository,
    InsertFavoriteMovieRepository, DeleteFavoriteMovieRepository,
    GetFavoriteMovieDetaileRepository {

    override suspend fun detaileMovie(movieId: Int): Resource<ResponseDetaile> {
        return safeApiCall { api.detaileMovie(movieId = movieId) }
    }

    override suspend fun existFavoriteMovie(id: Int): Boolean {
        return dao.existsFavoriteMovie(id)
    }

    override suspend fun insertFavoriteMovie(entity: FavoriteMovieEntity) {
        dao.insertFavoriteMovie(entity)
    }

    override suspend fun deleteFavoriteMovie(entity: FavoriteMovieEntity) {
        dao.deleteFavoriteMovie(entity)
    }

    override suspend fun getFavoriteMovieDetaile(id: Int): FavoriteMovieEntity {
       return dao.getFavoriteMovie(id)
    }
}