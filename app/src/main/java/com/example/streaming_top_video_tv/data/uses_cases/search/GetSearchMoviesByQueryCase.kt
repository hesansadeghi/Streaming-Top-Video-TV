package com.example.streaming_top_video_tv.data.uses_cases.search

import com.example.streaming_top_video_tv.data.repositories.SearchRepository
import javax.inject.Inject

class GetSearchMoviesByQueryCase @Inject constructor(private val repository: SearchRepository) {

    suspend operator fun invoke(q: String) = repository.getsSearchMoviesByQueryRepository(q)

}