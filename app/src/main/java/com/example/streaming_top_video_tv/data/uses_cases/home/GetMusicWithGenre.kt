package com.example.streaming_top_video_tv.data.uses_cases.home

import com.example.streaming_top_video_tv.data.repositories.HomeRepository
import com.example.streaming_top_video_tv.util.Constants.MUSIC_ID
import jakarta.inject.Inject

class GetMusicWithGenre @Inject constructor(private val repository: HomeRepository){

    suspend operator fun invoke()=repository.getMoviesWithGenre(MUSIC_ID)
}