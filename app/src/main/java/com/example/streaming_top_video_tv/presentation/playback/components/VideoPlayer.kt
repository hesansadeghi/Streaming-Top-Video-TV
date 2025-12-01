package com.example.streaming_top_video_tv.presentation.playback.components

import androidx.activity.compose.LocalActivity
import androidx.annotation.OptIn
import androidx.compose.foundation.AndroidExternalSurface
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import com.example.streaming_top_video_tv.presentation.playback.AttachSurface
import com.example.streaming_top_video_tv.presentation.playback.DetachSurface
import com.example.streaming_top_video_tv.presentation.playback.PlayBackViewModel
import com.example.streaming_top_video_tv.util.ImmersiveSystemUi
import com.example.streaming_top_video_tv.util.Utils.popBackstack


@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    playerViewModel: PlayBackViewModel
) {

    val playerUiModel by playerViewModel.playerUiModel.collectAsState()

    val activity = LocalActivity.current

    if (playerUiModel.popBackStack) {

        ImmersiveSystemUi(false)
        popBackstack(activity!!, navController)
    }

    Box(modifier = Modifier.focusGroup()) {

        AndroidExternalSurface(
            modifier = modifier
//                .aspectRatio(playerUiModel.videoAspectRatio)
//                .clickable {
//
//                    playerViewModel.showPlayerControls()
//                }
        ) {
            onSurface { surface, _, _ ->

                playerViewModel.onEvent(AttachSurface(surface))
                surface.onDestroyed {

                    playerViewModel.onEvent(DetachSurface)
                }
            }
        }



        VideoOverlay(
            modifier = Modifier
                .matchParentSize()
                .focusGroup(),
            playerViewModel = playerViewModel,
            onSettingClick = {

                playerViewModel.openTrackSelector()
            },
            onVideoSpeedClick = {

                playerViewModel.openVideoSpeedSelector()
            },
            onAction = {

                playerViewModel.onEvent(playerUiEvent = it)
            },
        )

    }

}


