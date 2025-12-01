package com.example.streaming_top_video_tv.presentation.playback.components

import androidx.annotation.OptIn
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.DefaultTimeBar
import androidx.media3.ui.TimeBar
import com.example.streaming_top_video_tv.presentation.ui.theme.DarkTangerine
import com.example.streaming_top_video_tv.presentation.ui.theme.PurpleGrey40


@OptIn(UnstableApi::class)
@Composable
fun TimeBar(
    modifier: Modifier,
    positionInMs: Long,
    durationInMs: Long,
    bufferedPositionInMs: Long,
    onSeek: (Float) -> Unit
) {

    var isFocused by remember { mutableStateOf(false) }

    AndroidView(
        modifier = modifier
            .onFocusChanged { focusState ->
                isFocused = focusState.hasFocus
            }
            .clip(RoundedCornerShape(100.dp))
            .border(
                4.dp, if (isFocused) {
                    Color.Red
                } else {
                    Color.Transparent
                },
                RoundedCornerShape(100.dp)
            ),
        factory = { context ->

            DefaultTimeBar(context).apply {
                setScrubberColor(DarkTangerine.hashCode())
                setPlayedColor(DarkTangerine.hashCode())
                setBufferedColor(DarkTangerine.hashCode())
                setUnplayedColor(PurpleGrey40.hashCode())
            }

        },
        update = { timeBar ->

            with(timeBar) {

                addListener(object : TimeBar.OnScrubListener {
                    override fun onScrubStart(
                        timeBar: TimeBar,
                        position: Long
                    ) {
                    }

                    override fun onScrubMove(
                        timeBar: TimeBar,
                        position: Long
                    ) {
                    }

                    override fun onScrubStop(
                        timeBar: TimeBar,
                        position: Long,
                        canceled: Boolean
                    ) {
                        onSeek(position.toFloat())
                    }
                })

                setPosition(positionInMs)
                setDuration(durationInMs)
                setBufferedPosition(bufferedPositionInMs)

            }

        }
    )

}