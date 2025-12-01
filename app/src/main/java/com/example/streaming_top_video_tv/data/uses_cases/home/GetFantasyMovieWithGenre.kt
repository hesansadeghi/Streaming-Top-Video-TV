package com.example.streaming_top_video_tv.data.uses_cases.home

import com.example.streaming_top_video_tv.data.repositories.HomeRepository
import com.example.streaming_top_video_tv.util.Constants.FANTASY_MOVIE_ID
import jakarta.inject.Inject

class GetFantasyMovieWithGenre @Inject constructor(private val repository: HomeRepository){

    suspend operator fun invoke()=repository.getMoviesWithGenre(FANTASY_MOVIE_ID)
}