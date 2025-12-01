package com.example.streaming_top_video_tv.presentation.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.streaming_top_video_tv.models.NavigationItem
import com.example.streaming_top_video_tv.presentation.detaile.DetaileScreen
import com.example.streaming_top_video_tv.presentation.downloads.DownloadsScreen
import com.example.streaming_top_video_tv.presentation.favorite.FavoriteScreen
import com.example.streaming_top_video_tv.presentation.genre.GenreScreen
import com.example.streaming_top_video_tv.presentation.home.HomeScreen
import com.example.streaming_top_video_tv.presentation.main.components.NavBar
import com.example.streaming_top_video_tv.presentation.playback.PlayBackScreen
import com.example.streaming_top_video_tv.presentation.search.SearchScreen
import com.example.streaming_top_video_tv.presentation.ui.theme.MidnightDark
import com.example.streaming_top_video_tv.util.Constants.DETAILE
import com.example.streaming_top_video_tv.util.Constants.DOWNLOADS
import com.example.streaming_top_video_tv.util.Constants.FAVORITE
import com.example.streaming_top_video_tv.util.Constants.GENRE
import com.example.streaming_top_video_tv.util.Constants.GENRE_ID
import com.example.streaming_top_video_tv.util.Constants.GENRE_TITLE
import com.example.streaming_top_video_tv.util.Constants.HOME
import com.example.streaming_top_video_tv.util.Constants.MOVIE_ID
import com.example.streaming_top_video_tv.util.Constants.MOVIE_TITLE
import com.example.streaming_top_video_tv.util.Constants.PLAY_MOVIE
import com.example.streaming_top_video_tv.util.Constants.SEARCH
import com.example.streaming_top_video_tv.util.Constants.STREAM_URL
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {

    var selectedMenu by rememberSaveable {

        mutableStateOf(NavigationItem.Home.route)
    }

    val navController = rememberNavController()
    var fullScreen by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }



//    SetStatusBarColor(false)


    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightDark)
    ) {

        Scaffold(
            modifier = Modifier
                .background(MidnightDark)
                .fillMaxSize()
                .weight(1f), snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) {

            NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MidnightDark)
                    .padding(it),
                startDestination = HOME,
                navController = navController
            ) {

                composable(HOME) {

                    fullScreen = false

                    HomeScreen(
                        navController = navController,
                        errorMessage = { message ->

                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(
                                    message = message,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        })

                    if (selectedMenu != HOME) {
                        selectedMenu = HOME
                    }

                }

                composable(FAVORITE) {

                    FavoriteScreen(navController)

                    fullScreen = false


                    if (selectedMenu != FAVORITE) {
                        selectedMenu = FAVORITE
                    }
                }

                composable(DOWNLOADS) {


                    DownloadsScreen(navController)

                    fullScreen = false


                    if (selectedMenu != DOWNLOADS) {
                        selectedMenu = DOWNLOADS
                    }

                }

                composable(SEARCH) {


                    SearchScreen(
                        navController, errorMessage = { message ->

                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(
                                    message = message,
                                    duration = SnackbarDuration.Short
                                )
                            }

                        })

                    fullScreen = false

                    if (selectedMenu != SEARCH) {
                        selectedMenu = SEARCH
                    }

                }

                val genreRoute = "$GENRE?$GENRE_ID={genreId}&$GENRE_TITLE={genreTitle}"

                composable(
                    route = genreRoute,
                    arguments = listOf(
                        navArgument(name = GENRE_ID) {
                            type = NavType.IntType
                        },
                        navArgument(name = GENRE_TITLE) {
                            type = NavType.StringType
                        }
                    )) { backstackEntry ->


                    backstackEntry.arguments?.getInt(GENRE_ID)?.let { genreId ->


                        backstackEntry.arguments?.getString(GENRE_TITLE)?.let { genreTitle ->

                            GenreScreen(
                                navController = navController,
                                genreId = genreId, genreTitle,
                                errorMessage = { errorMessage ->
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = errorMessage,
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                })

                            fullScreen = false

                        }
                    }
                }

                val playMovieRoute =
                    "$PLAY_MOVIE?$MOVIE_ID={movieId}&$STREAM_URL={streamUrl}&$MOVIE_TITLE={movieTitle}"

                composable(
                    route = playMovieRoute,
                    arguments = listOf(
                        navArgument(name = MOVIE_ID) {
                            type = NavType.StringType
                        },
                        navArgument(name = STREAM_URL) {
                            type = NavType.StringType
                        },
                        navArgument(name = MOVIE_TITLE) {
                            type = NavType.StringType
                        }
                    )
                ) { backstackEntry ->

                    backstackEntry.arguments?.let { argument ->

                        argument.getString(STREAM_URL)?.let { streamUrl ->


                            argument.getString(MOVIE_TITLE)?.let { movieTitle ->


                                argument.getString(MOVIE_ID)?.let { movieId ->

                                    PlayBackScreen(
                                        navController,
                                        streamUrl,
                                        movieId,
                                        movieTitle
                                    )

                                    fullScreen = true


                                }
                            }

                        }


                    }

                }

                val detaileRoute = "$DETAILE?$MOVIE_ID={movieId}"

                composable(
                    route = detaileRoute,
                    arguments = listOf(
                        navArgument(name = MOVIE_ID) {
                            type = NavType.StringType
                        }
                    )
                ) { backstackEntry ->

                    backstackEntry.arguments?.let { argument ->

                        argument.getString(MOVIE_ID)?.let { movieId ->

                            DetaileScreen(
                                navController = navController,
                                movieId = movieId,
                                errorMessage = { errorMessage ->

                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = errorMessage,
                                            duration = SnackbarDuration.Short
                                        )
                                    }

                                })

                            fullScreen = false

                        }

                    }

                }


            }

        }

        if (fullScreen.not()) {

            NavBar(navController, selectedMenu) { isSelectedMenuIndex ->

                selectedMenu = isSelectedMenuIndex

            }

        }
    }


}

