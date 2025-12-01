package com.example.streaming_top_video_tv.presentation.search.components

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.streaming_top_video_tv.presentation.genre.components.MoviesLazyVerticalStaggeredGrid
import com.example.streaming_top_video_tv.presentation.search.SearchUiEvent
import com.example.streaming_top_video_tv.presentation.search.SearchViewModel
import com.example.streaming_top_video_tv.util.Constants.DETAILE
import com.example.streaming_top_video_tv.util.Constants.MOVIE_ID
import com.example.streaming_top_video_tv.util.Utils.popBackstack


@Composable
fun InitSearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel,
    errorMessage: (String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    val activity = LocalActivity.current

    val detaileMovieNavigate: Int? = viewModel.detaileMovieNavigate.collectAsState().value

    if (uiState.popBackStack) {

        popBackstack(activity!!, navController)
    }

    LaunchedEffect(detaileMovieNavigate) {
        detaileMovieNavigate?.let { movieId ->

            try {
                navController.navigate("$DETAILE?$MOVIE_ID=$movieId")
                viewModel.onEvent(SearchUiEvent.ClickedItemMovie(null))
            } catch (e: Exception) {
                Log.e("NavigationError", "Error navigating to playMovie: ${e.message}")
            }
        }
    }


    if (uiState.isLoading) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {

            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = Color.White
            )
        }

    } else {

        uiState.moviesList?.let {

            MoviesLazyVerticalStaggeredGrid(it) { movieId ->


                viewModel.onEvent(SearchUiEvent.ClickedItemMovie(movieId))
            }
        }

        uiState.errorMessage?.let{

            errorMessage(it)
        }

    }

}