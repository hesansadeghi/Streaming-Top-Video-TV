package com.example.streaming_top_video_tv.data.repositories

import com.example.streaming_top_video_tv.data.db.dao.DownloadedMoviesDao
import com.example.streaming_top_video_tv.data.db.entities.DownloadedMovieEntity
import com.example.streaming_top_video_tv.data.repositories.interfaces.DeleteDownloadedMovieRepository
import com.example.streaming_top_video_tv.data.repositories.interfaces.GetAllDownloadedMoviesRepository
import jakarta.inject.Inject

class DownloadsRepository @Inject constructor(private val dao: DownloadedMoviesDao) :
    GetAllDownloadedMoviesRepository,
    DeleteDownloadedMovieRepository {
    override suspend fun getAllDownloadedMovies(): MutableList<DownloadedMovieEntity> {
        return dao.getAllDownloadedMovies()
    }

    override suspend fun deleteDownloadedMovie(entity: DownloadedMovieEntity) {
        dao.deleteDownloadedMovie(entity)
    }
}