package com.example.streaming_top_video_tv.data.uses_cases.downloads

import com.example.streaming_top_video_tv.data.repositories.DownloadsRepository
import jakarta.inject.Inject

class GetAllDownloadedMoviesCase @Inject constructor(private val repository: DownloadsRepository) {

    suspend operator fun invoke() = repository.getAllDownloadedMovies()
}