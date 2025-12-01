package com.example.streaming_top_video_tv.presentation.detaile

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.streaming_top_video_tv.presentation.detaile.components.InitDetaileScreen


@Composable
fun DetaileScreen(
    navController: NavHostController,
    movieId: String,
    viewModel: DetaileViewModel = hiltViewModel(),
    errorMessage: (String) -> Unit
) {


    BackHandler {

        viewModel.onEvent(DetaileUiEvent.BackStack)
    }

    viewModel.onEvent(DetaileUiEvent.GetDetaileMovieById(movieId.toInt()))

    InitDetaileScreen(
        navController,
        viewModel,
        { errorMessage ->

            errorMessage(errorMessage)
        })


}