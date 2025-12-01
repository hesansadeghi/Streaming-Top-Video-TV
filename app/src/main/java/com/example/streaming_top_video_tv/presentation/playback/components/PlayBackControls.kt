package com.example.streaming_top_video_tv.presentation.playback.components

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import com.example.streaming_top_video_tv.R
import com.example.streaming_top_video_tv.presentation.playback.FastForward
import com.example.streaming_top_video_tv.presentation.playback.Pause
import com.example.streaming_top_video_tv.presentation.playback.PlaybackState.BUFFERING
import com.example.streaming_top_video_tv.presentation.playback.PlaybackState.COMPLETED
import com.example.streaming_top_video_tv.presentation.playback.PlaybackState.ERROR
import com.example.streaming_top_video_tv.presentation.playback.PlaybackState.IDLE
import com.example.streaming_top_video_tv.presentation.playback.PlaybackState.PAUSE
import com.example.streaming_top_video_tv.presentation.playback.PlaybackState.PLAYING
import com.example.streaming_top_video_tv.presentation.playback.PlayerUiEvent
import com.example.streaming_top_video_tv.presentation.playback.PlayerUiModel
import com.example.streaming_top_video_tv.presentation.playback.Resume
import com.example.streaming_top_video_tv.presentation.playback.Rewind
import com.example.streaming_top_video_tv.presentation.playback.Seek
import com.example.streaming_top_video_tv.presentation.playback.Start
import com.example.streaming_top_video_tv.presentation.playback.isReady
import com.example.streaming_top_video_tv.presentation.ui.theme.DarkTangerine


@OptIn(UnstableApi::class)
@Composable
fun PlayBackControls(
    modifier: Modifier = Modifier,
    playerUiModel: PlayerUiModel,
    onSettingClick: () -> Unit,
    onVideoSpeedClick: () -> Unit,
    onAction: (PlayerUiEvent) -> Unit,
) {

    Box(
        modifier = modifier
            .focusGroup()
            .background(Color.Transparent)
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .focusGroup()
                .align(Alignment.TopEnd),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = playerUiModel.titleMovie,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )


        }


        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .focusGroup(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (playerUiModel.playbackState == BUFFERING) {

                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = Color.White
                )
            }


            if (playerUiModel.playbackState == COMPLETED) {

                PlayBackButton(
                    modifier = Modifier
                        .size(65.dp)
                        .focusable(),
                    resourceId = R.drawable.icons8_replay_50,
                    description = "Replay",
                    color = Color.White
                ) {

                    onAction(Start(0))
                }
            }


            if (playerUiModel.playbackState == ERROR) {

//                PlayBackButton(
//                    modifier = Modifier.size(65.dp),
//                    resourceId = R.drawable.icons8_error_50,
//                    description = "Error",
//                    color = null
//                ) {
//
//
//                }

                Image(
                    modifier = Modifier.size(45.dp),
                    contentDescription = "Error",
                    painter = painterResource(R.drawable.icons8_error_50)
                )


                PlayBackButton(
                    modifier = Modifier.size(65.dp),
                    resourceId = R.drawable.icons8_replay_50,
                    description = "Replay",
                    color = Color.White
                ) {

                    onAction(Start(playerUiModel.timelineUiModel?.currentPositionInMs))
                }
            }


        }



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .focusGroup()
        ) {

            playerUiModel.timelineUiModel?.let { timeLine ->


                PlayBackPosition(
                    contentDurationInMs = timeLine.durationInMs,
                    contentPositionInMs = timeLine.currentPositionInMs
                )

                TimeBar(
                    modifier = Modifier.fillMaxWidth(),
                    positionInMs = timeLine.currentPositionInMs,
                    durationInMs = timeLine.durationInMs,
                    bufferedPositionInMs = timeLine.bufferedPositionInMs
                ) {

                    onAction(Seek(it.toLong()))
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusGroup(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (playerUiModel.playbackState == PLAYING) {

                    PlayBackCircleButton(
                        modifier = Modifier.size(50.dp),
                        resourceId = R.drawable.icons8_pause_64,
                        description = "pause",
                        color = Color.White
                    ) {

                        onAction(Pause)
                    }
                }


                if (playerUiModel.playbackState == PAUSE) {

                    PlayBackCircleButton(
                        modifier = Modifier.size(50.dp),
                        resourceId = R.drawable.icons8_play_64,
                        description = "play",
                        color = Color.White
                    ) {

                        onAction(Resume)
                    }

                }


                if (playerUiModel.playbackState == IDLE) {

                    PlayBackCircleButton(
                        modifier = Modifier.size(50.dp),
                        resourceId = R.drawable.icons8_play_64,
                        description = "Start",
                        color = Color.White
                    ) {

                        onAction(Start())
                    }
                }


                if (playerUiModel.playbackState.isReady()) {

                    PlayBackButton(
                        modifier = Modifier.size(60.dp),
                        resourceId = R.drawable.icons8_replay_10_96,
                        description = "Fast forward",
                        color = DarkTangerine
                    ) {

                        onAction(Rewind(10_000))

                    }


                    PlayBackButton(
                        modifier = Modifier.size(60.dp),
                        resourceId = R.drawable.icons8_forward_10_96,
                        description = "Fast forward",
                        color = DarkTangerine
                    ) {

                        onAction(FastForward(10_000))

                    }

                }



                Spacer(modifier = Modifier.weight(1f))


                PlayBackButton(
                    modifier = Modifier.size(60.dp),
                    resourceId = R.drawable.baseline_speed_24,
                    description = "Open Video Speed Selector",
                    color = DarkTangerine
                ) {

                    onVideoSpeedClick()
                }


                PlayBackButton(
                    modifier = Modifier.size(60.dp),
                    resourceId = R.drawable.icons8_setting_50,
                    description = "Open Track Selector",
                    color = DarkTangerine
                ) {

                    onSettingClick()
                }


            }


        }


    }
}