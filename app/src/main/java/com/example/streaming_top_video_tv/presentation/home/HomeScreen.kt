@file:kotlin.OptIn(ExperimentalPermissionsApi::class)

package com.example.streaming_top_video_tv.presentation.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import com.example.streaming_top_video_tv.data.models.ResponseGenresList.ResponseGenresListItem
import com.example.streaming_top_video_tv.models.PlayMovieModel
import com.example.streaming_top_video_tv.presentation.home.components.GenresLazyRow
import com.example.streaming_top_video_tv.presentation.home.components.HomeScreenShimmer
import com.example.streaming_top_video_tv.presentation.home.components.MoviesLazyRow
import com.example.streaming_top_video_tv.presentation.home.components.ShowFocusedStreamingMovie
import com.example.streaming_top_video_tv.presentation.home.components.StreamingMoviesLazyRow
import com.example.streaming_top_video_tv.presentation.ui.theme.MidnightDark
import com.example.streaming_top_video_tv.util.Constants.DETAILE
import com.example.streaming_top_video_tv.util.Constants.FANTASY
import com.example.streaming_top_video_tv.util.Constants.GENRE
import com.example.streaming_top_video_tv.util.Constants.GENRE_ID
import com.example.streaming_top_video_tv.util.Constants.GENRE_TITLE
import com.example.streaming_top_video_tv.util.Constants.LAST_MOVIES
import com.example.streaming_top_video_tv.util.Constants.MOVIE_ID
import com.example.streaming_top_video_tv.util.Constants.MOVIE_TITLE
import com.example.streaming_top_video_tv.util.Constants.MUSIC
import com.example.streaming_top_video_tv.util.Constants.PLAY_MOVIE
import com.example.streaming_top_video_tv.util.Constants.STREAM_URL
import com.example.streaming_top_video_tv.util.DownloadEventBus
import com.example.streaming_top_video_tv.util.ImmersiveSystemUi
import com.example.streaming_top_video_tv.util.ThisApp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@SuppressLint("InlinedApi")
@OptIn(UnstableApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    errorMessage: (String) -> Unit
) {

    Log.i("screen", "HomeScreen")

    val uiState by viewModel.uiState.collectAsState()

    var isSelectedStreamingMovie: StreamingMovie? = null

    val notificationPermission = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    ) {
        isSelectedStreamingMovie?.let { card ->

            viewModel.onEvent(HomeUiEvent.DownloadMovie(card))
            isSelectedStreamingMovie = null
        }
    }


    val playMovie: PlayMovieModel? = viewModel.playMovie.collectAsState().value
    val detaileMovieNavigate: Int? = viewModel.detaileMovieNavigate.collectAsState().value
    val moviesGenreNavigate: ResponseGenresListItem? =
        viewModel.moviesGenreNavigate.collectAsState().value


    var showStreamingMovie by remember { mutableStateOf(uiState.streamingMovies[0]) }



    DownloadEventBus.listener = { download ->

        viewModel.updateDownloadState(download)
    }


    ImmersiveSystemUi(false)


    LaunchedEffect(playMovie) {
        playMovie?.let { playMovie ->

            try {
                navController.navigate("$PLAY_MOVIE?$MOVIE_ID=${playMovie.id}&$STREAM_URL=${playMovie.streamUrl}&$MOVIE_TITLE=${playMovie.movieTitle}")
                viewModel.onEvent(HomeUiEvent.PlayMovie(null, null, null))
            } catch (e: Exception) {
                Log.e("NavigationError", "Error navigating to playMovie: ${e.message}")
            }
        }
    }

    LaunchedEffect(detaileMovieNavigate) {
        detaileMovieNavigate?.let { movieId ->

            try {
                navController.navigate("$DETAILE?$MOVIE_ID=$movieId")
                viewModel.onEvent(HomeUiEvent.ClickedItemMovie(null))
            } catch (e: Exception) {
                Log.e("NavigationError", "Error navigating to detaileMovie: ${e.message}")
            }
        }
    }


    LaunchedEffect(moviesGenreNavigate) {
        moviesGenreNavigate?.let { genre ->

            try {
                navController.navigate("$GENRE?$GENRE_ID=${genre.id}&$GENRE_TITLE=${genre.name}")
                viewModel.onEvent(HomeUiEvent.ClickedItemGenre(null))
            } catch (e: Exception) {
                Log.e("NavigationError", "Error navigating to genreMovie: ${e.message}")
            }
        }
    }

    if (uiState.isLoading) {


        HomeScreenShimmer()
    } else {



        LazyColumn(
            modifier = Modifier
                .background(MidnightDark)
                .fillMaxWidth()
        ) {


            item {

                ShowFocusedStreamingMovie(
                    showStreamingMovie = showStreamingMovie,
                    viewModel = viewModel,
                    playItem = { streamMovie, isDownloaded ->

                        viewModel.onEvent(
                            HomeUiEvent.PlayMovie(
                                streamMovie.streamUrl,
                                streamMovie.id.toString(),
                                streamMovie.title
                            )
                        )

                        ThisApp.isLocaleMovie = isDownloaded
                    },
                    downLoadItem = { card ->

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                            if (!notificationPermission.status.isGranted) {

                                isSelectedStreamingMovie = card
                                notificationPermission.launchPermissionRequest()
                            } else {
                                viewModel.onEvent(HomeUiEvent.DownloadMovie(card))
                            }

                        } else {

                            viewModel.onEvent(HomeUiEvent.DownloadMovie(card))
                        }


                    },
                    cancelDownLoadItem = { downloadId ->

                        viewModel.onEvent(HomeUiEvent.CancelDownloadMovie(downloadId))
                    }
                )

            }


            item {

                StreamingMoviesLazyRow(
                    streamingMovieList = uiState.streamingMovies,
                    viewModel = viewModel,
                    playItem = { streamMovie, isDownloaded ->

                        viewModel.onEvent(
                            HomeUiEvent.PlayMovie(
                                streamMovie.streamUrl,
                                streamMovie.id.toString(),
                                streamMovie.title
                            )
                        )

                        ThisApp.isLocaleMovie = isDownloaded
                    },
                    showStreamingMovie = { movie ->

                        showStreamingMovie = movie
                    }
                )
            }


            item {

                uiState.genresList?.let {

                    GenresLazyRow(it) { genre ->

                        viewModel.onEvent(HomeUiEvent.ClickedItemGenre(genre))
                    }
                }
            }


            item {

                uiState.moviesList?.let {

                    MoviesLazyRow(LAST_MOVIES, it) { movieId ->

                        viewModel.onEvent(HomeUiEvent.ClickedItemMovie(movieId))
                    }
                }

            }


            item {

                uiState.fantasyMoviesList?.let {

                    MoviesLazyRow(FANTASY, it) { movieId ->

                        viewModel.onEvent(HomeUiEvent.ClickedItemMovie(movieId))
                    }
                }

            }


            item {

                uiState.musicList?.let {

                    MoviesLazyRow(MUSIC, it) { movieId ->

                        viewModel.onEvent(HomeUiEvent.ClickedItemMovie(movieId))
                    }
                }

            }


        }

        uiState.errorMessage?.let { message ->

            errorMessage(message)
        }

    }


}