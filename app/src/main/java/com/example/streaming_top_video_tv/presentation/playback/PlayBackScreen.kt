package com.example.streaming_top_video_tv.presentation.playback

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import com.example.streaming_top_video_tv.presentation.playback.components.TrackSelector
import com.example.streaming_top_video_tv.presentation.playback.components.VideoPlayer
import com.example.streaming_top_video_tv.presentation.playback.components.VideoSpeedSelector
import com.example.streaming_top_video_tv.util.ImmersiveSystemUi


@OptIn(UnstableApi::class)
@Composable
fun PlayBackScreen(
    navController: NavHostController,
    streamUrl: String,
    movieId: String,
    movieTitle: String,
    playerViewModel: PlayBackViewModel = hiltViewModel()
) {

    Log.i("screen", "PlayBackScreen")


    ImmersiveSystemUi(true)
    LaunchedEffect(true) {


        playerViewModel.onEvent(Init(streamUrl, movieId, movieTitle))
        playerViewModel.onEvent(Start())
    }


    BackHandler {

        playerViewModel.onEvent(BackStack)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusGroup(),
        contentAlignment = Alignment.Center
    ) {

        VideoPlayer(
            playerViewModel = playerViewModel,
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .focusGroup()
        )


        TrackSelector(
            playerViewModel = playerViewModel,
            onVideoTrackSelected = {
                playerViewModel.onEvent(SetVideoTrack(it))
            },
            onAudioTrackSelected = {
                playerViewModel.onEvent(SetAudioTrack(it))
            },
            onSubtitleTrackSelected = {
                playerViewModel.onEvent(SetSubtitleTrack(it))
            },
            onDismiss = {
                playerViewModel.hideTrackSelector()
            }
        )


        VideoSpeedSelector(
            playerViewModel = playerViewModel,
            onVideoSpeedSelected = {
                playerViewModel.onEvent(SetPlaybackParameters(it))
            },
            onDismiss = {
                playerViewModel.hideVideoSpeedSelector()
            }
        )

    }

}



