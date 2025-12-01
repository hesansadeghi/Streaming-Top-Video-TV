package com.example.streaming_top_video_tv.data.uses_cases.home

import com.example.streaming_top_video_tv.data.repositories.HomeRepository
import javax.inject.Inject

class LastMoviesCase @Inject constructor(private val repository: HomeRepository){

    suspend operator fun invoke() = repository.lastMovies()

}