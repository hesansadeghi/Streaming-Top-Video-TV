package com.example.streaming_top_video_tv.data.uses_cases.downloads

data class DownloadsUseCase(
    val getAllDownloadedMoviesCase: GetAllDownloadedMoviesCase,
    val deleteDownloadedMovieUseCase: DeleteDownloadedMovieUseCase
)
