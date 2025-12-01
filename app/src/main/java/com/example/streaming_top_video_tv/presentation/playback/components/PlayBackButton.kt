package com.example.streaming_top_video_tv.presentation.playback.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun PlayBackButton(
    modifier: Modifier = Modifier,
    @DrawableRes resourceId: Int,
    description: String,
    color: Color?,
    onClick: () -> Unit,
) {



    var isFocused by remember { mutableStateOf(false) }

    Box (
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
            )
            .clickable(onClick = onClick)
    ){

        Image(
            modifier = Modifier.fillMaxSize().padding(6.dp),
            contentDescription = description,
            painter = painterResource(resourceId),
            colorFilter = color?.let { ColorFilter.tint(color) }
        )

    }


}
