package com.example.streaming_top_video_tv.presentation.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.streaming_top_video_tv.presentation.search.components.InitSearchScreen
import com.example.streaming_top_video_tv.presentation.search.components.SearchTopAppBar


@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
    errorMessage: (String) -> Unit
) {


    BackHandler {

        viewModel.onEvent(SearchUiEvent.BackStack)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        SearchTopAppBar { text ->

            viewModel.onEvent(SearchUiEvent.SearchMoviesByQuery(text))
        }

        InitSearchScreen(navController, viewModel) { message ->

            errorMessage(message)
        }

    }


}