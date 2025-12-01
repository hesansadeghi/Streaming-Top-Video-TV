package com.example.streaming_top_video_tv.presentation.genre

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.streaming_top_video_tv.presentation.genre.components.InitGenresScreen


@Composable
fun GenreScreen(
    navController: NavHostController,
    genreId: Int,
    genreTitle: String,
    viewModel: GenreViewModel = hiltViewModel(),
    errorMessage: (String) -> Unit
) {

    BackHandler {

        viewModel.onEvent(GenreUiEvent.BackStack)
    }

    viewModel.onEvent(GenreUiEvent.GetMoviesByGenre(genreId))


    InitGenresScreen(
        genreTitle,
        navController,
        viewModel
    ) {

        errorMessage(it)
    }


}