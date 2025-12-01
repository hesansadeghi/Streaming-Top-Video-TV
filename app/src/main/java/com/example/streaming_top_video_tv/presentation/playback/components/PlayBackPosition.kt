package com.example.streaming_top_video_tv.presentation.playback.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import com.example.streaming_top_video_tv.util.Utils


@Composable
fun PlayBackPosition(
    contentPositionInMs: Long,
    contentDurationInMs: Long
) {

    val contentPositionString = Utils.formatMsToString(contentPositionInMs)
    val contentDurationString = Utils.formatMsToString(contentDurationInMs)

    Text(
        text = "$contentPositionString / $contentDurationString",
        fontSize = 14.sp,
        color = Color.White
    )

}