package com.example.streaming_top_video_tv.data.repositories

import com.example.streaming_top_video_tv.data.api.ApiService
import com.example.streaming_top_video_tv.data.db.dao.DownloadedMoviesDao
import com.example.streaming_top_video_tv.data.db.entities.DownloadedMovieEntity
import com.example.streaming_top_video_tv.data.models.ResponseGenresList
import com.example.streaming_top_video_tv.data.models.ResponseMoviesList
import com.example.streaming_top_video_tv.data.repositories.interfaces.ExistsDownloadedMovieRepository
import com.example.streaming_top_video_tv.data.repositories.interfaces.GenresRepository
import com.example.streaming_top_video_tv.data.repositories.interfaces.InsertDownloadedMovieRepository
import com.example.streaming_top_video_tv.data.repositories.interfaces.LastMoviesRepository
import com.example.streaming_top_video_tv.data.repositories.interfaces.MoviesWithGenreRepository
import com.example.streaming_top_video_tv.util.Resource
import com.example.streaming_top_video_tv.util.safeApiCall
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: ApiService,
    private val dao: DownloadedMoviesDao
) : LastMoviesRepository, GenresRepository, MoviesWithGenreRepository,
    ExistsDownloadedMovieRepository, InsertDownloadedMovieRepository {

    override suspend fun lastMovies(): Resource<ResponseMoviesList> {

        return safeApiCall { api.lastMovies() }
    }


    override suspend fun getGenres(): Resource<ResponseGenresList> {

        return safeApiCall { api.getGenres() }
    }


    override suspend fun getMoviesWithGenre(id: Int): Resource<ResponseMoviesList> {

        return safeApiCall { api.getMoviesWithGenre(id) }
    }

    override suspend fun existDownloadedMovie(id: String): Boolean {

        return dao.existsDownloadedMovie(id)
    }

    override suspend fun insertDownloadedMovie(entity: DownloadedMovieEntity) {

        dao.insertDownloadedMovie(entity)
    }

}

