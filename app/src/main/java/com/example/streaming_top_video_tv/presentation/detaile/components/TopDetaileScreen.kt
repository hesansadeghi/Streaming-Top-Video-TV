package com.example.streaming_top_video_tv.presentation.detaile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.streaming_top_video_tv.R
import com.example.streaming_top_video_tv.presentation.playback.components.PlayBackButton


@Composable
fun TopDetaileScreen(
    isFavorite: Boolean,
    setFavorite: (Boolean) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .systemBarsPadding()
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {


        PlayBackButton(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            resourceId = if (isFavorite) {
                R.drawable.baseline_favorite_24
            } else {

                R.drawable.baseline_favorite_border_24
            },
            description = "Favorite button",
            if (isFavorite) {
                Color.Red
            }else{
                Color.LightGray
            }
        ) {

            setFavorite(isFavorite.not())
        }



    }

}