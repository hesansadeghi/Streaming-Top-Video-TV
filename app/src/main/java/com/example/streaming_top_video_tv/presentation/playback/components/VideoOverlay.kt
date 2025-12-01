package com.example.streaming_top_video_tv.presentation.playback.components

import androidx.annotation.OptIn
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.media3.common.util.UnstableApi
import com.example.streaming_top_video_tv.presentation.playback.PlayBackViewModel
import com.example.streaming_top_video_tv.presentation.playback.PlayerUiEvent


@OptIn(UnstableApi::class)
@Composable
fun VideoOverlay(
    modifier: Modifier = Modifier,
    playerViewModel: PlayBackViewModel,
    onSettingClick:  () -> Unit,
    onVideoSpeedClick:  () -> Unit,
    onAction:  (PlayerUiEvent) -> Unit,
) {

    val playerUiModel by playerViewModel.playerUiModel.collectAsState()

    Box(
        modifier = modifier.focusGroup()
            .focusable()
            .onKeyEvent {
                playerViewModel.onUserInteraction()
                false
            }
    ) {


        if (playerUiModel.playerControlsVisible) {
            PlayBackControls(
                modifier = Modifier
                    .matchParentSize()
                    .focusGroup()
                ,
                playerUiModel = playerUiModel,
                onSettingClick = onSettingClick,
                onVideoSpeedClick = onVideoSpeedClick,
                onAction = onAction
            )
        }

        Subtitle(
            modifier = Modifier
                .matchParentSize(),
            cues = playerUiModel.currentSubtitles
        )

    }
}