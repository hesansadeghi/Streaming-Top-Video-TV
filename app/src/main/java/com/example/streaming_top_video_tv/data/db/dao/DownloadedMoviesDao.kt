package com.example.streaming_top_video_tv.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.streaming_top_video_tv.data.db.entities.DownloadedMovieEntity
import com.example.streaming_top_video_tv.util.Constants

@Dao
interface DownloadedMoviesDao {


    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertDownloadedMovie(entity: DownloadedMovieEntity)


    @Delete
    suspend fun deleteDownloadedMovie(entity: DownloadedMovieEntity)


    @Query("SELECT * FROM ${Constants.DOWNLOADED_MOVIE_TABLE}")
    fun getAllDownloadedMovies(): MutableList<DownloadedMovieEntity>


    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.DOWNLOADED_MOVIE_TABLE} WHERE movieId = :movieId)")
    suspend fun existsDownloadedMovie(movieId: String): Boolean

}