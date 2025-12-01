package com.example.streaming_top_video_tv.di

import android.content.Context
import androidx.room.Room
import com.example.streaming_top_video_tv.data.db.MoviesDataBase
import com.example.streaming_top_video_tv.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {


    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        MoviesDataBase::class.java,
        Constants.MOVIES_DATA_BASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration(false)
        .build()


    @Provides
    @Singleton
    fun provideDownloadedMoviesDao(db: MoviesDataBase) = db.downloadedMoviesDao()


    @Provides
    @Singleton
    fun provideFavoriteMoviesDao(db: MoviesDataBase) = db.favoriteMoviesDao()


}