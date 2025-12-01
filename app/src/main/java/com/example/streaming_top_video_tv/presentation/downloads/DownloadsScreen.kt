package com.example.streaming_top_video_tv.presentation.downloads

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.streaming_top_video_tv.R
import com.example.streaming_top_video_tv.models.PlayMovieModel
import com.example.streaming_top_video_tv.presentation.downloads.components.DownloadedMoviesCard
import com.example.streaming_top_video_tv.presentation.ui.theme.DarkTangerine
import com.example.streaming_top_video_tv.util.Constants.MOVIE_ID
import com.example.streaming_top_video_tv.util.Constants.MOVIE_TITLE
import com.example.streaming_top_video_tv.util.Constants.PLAY_MOVIE
import com.example.streaming_top_video_tv.util.Constants.STREAM_URL
import com.example.streaming_top_video_tv.util.ImmersiveSystemUi
import com.example.streaming_top_video_tv.util.ThisApp
import kotlinx.coroutines.flow.map


@Composable
fun DownloadsScreen(
    navController: NavHostController,
    viewModel: DownloadsViewModel = hiltViewModel()
) {

    val activity = LocalActivity.current

    val uiState by viewModel.uiState.collectAsState()

    val playMovie: PlayMovieModel? = viewModel.playMovie.collectAsState().value


    ImmersiveSystemUi(false)


    LaunchedEffect(playMovie) {
        playMovie?.let { playMovie ->


            try {
                navController.navigate("$PLAY_MOVIE?$MOVIE_ID=${playMovie.id}&$STREAM_URL=${playMovie.streamUrl}&$MOVIE_TITLE=${playMovie.movieTitle}")
                viewModel.onEvent(DownloadsUiEvent.PlayMovie(null, null,null))
            } catch (e: Exception) {
                Log.e("NavigationError", "Error navigating to playMovie: ${e.message}")
            }
        }
    }


    if (uiState.downloadedMovies.isNotEmpty()){

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp)
            ) {

            items(uiState.downloadedMovies){ item->

                DownloadedMoviesCard(
                    item ,
                    { movieEntity ->

                        viewModel.onEvent(
                            DownloadsUiEvent.PlayMovie(
                                movieEntity.streamUrl,
                                movieEntity.movieId,
                                movieEntity.title
                            )
                        )

                        ThisApp.isLocaleMovie = true

                    },{ movieEntity->


                        viewModel.onEvent(DownloadsUiEvent.DeleteDownloadedMovie(movieEntity))
                    }
                )

            }


        }


    }else{

        Box(contentAlignment = Alignment.Center){

            Icon(
                painter = painterResource(R.drawable.outline_file_download_off_150),
                contentDescription = "Downloads List is Empty",
                tint = DarkTangerine )
        }
    }


}