package com.example.streaming_top_video_tv.data.uses_cases.home

data class HomeUseCase(
    val lastMoviesCase: LastMoviesCase,
    val getGenresCase: GetGenresCase,
    val getMusicWithGenre: GetMusicWithGenre,
    val getFantasyMovieWithGenre: GetFantasyMovieWithGenre,
    val getExistsDownloadedMovieCase: GetExistsDownloadedMovieCase,
    val insertDownloadedMovieCase: InsertDownloadedMovieCase
)