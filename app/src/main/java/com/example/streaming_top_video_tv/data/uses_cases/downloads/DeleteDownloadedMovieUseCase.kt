package com.example.streaming_top_video_tv.data.uses_cases.downloads

import com.example.streaming_top_video_tv.data.db.entities.DownloadedMovieEntity
import com.example.streaming_top_video_tv.data.repositories.DownloadsRepository
import jakarta.inject.Inject

class DeleteDownloadedMovieUseCase @Inject constructor(private val repository: DownloadsRepository){

    suspend operator fun invoke(entity: DownloadedMovieEntity) = repository.deleteDownloadedMovie(entity)
}