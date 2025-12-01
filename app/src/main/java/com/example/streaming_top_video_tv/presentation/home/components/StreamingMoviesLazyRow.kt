package com.example.streaming_top_video_tv.presentation.home.components

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import com.example.streaming_top_video_tv.presentation.home.HomeViewModel
import com.example.streaming_top_video_tv.presentation.home.StreamingMovie
import com.example.streaming_top_video_tv.presentation.ui.theme.MidnightDark


@OptIn(UnstableApi::class)
@Composable
fun StreamingMoviesLazyRow(
    streamingMovieList: List<StreamingMovie>,
    viewModel: HomeViewModel,
    playItem: (StreamingMovie, Boolean) -> Unit,
    showStreamingMovie: (StreamingMovie) -> Unit,
) {

//    val downloads by viewModel.downloads.collectAsState()


        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MidnightDark)
        ) {

            items(streamingMovieList) { item ->

//                val downloadUiModel = downloads[item.id.toString()]
//
//                Log.e("StreamingMoviesLazyRow",downloadUiModel?.bytesDownloaded.toString())

                var isFocused by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(170.dp)
                        .padding(horizontal = 8.dp)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.hasFocus
                            if (isFocused){
                                showStreamingMovie(item)
                            }

                        }
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            2.dp, if (isFocused) {
                                Color.Red
                            } else {
                                Color.Gray
                            },
                            RoundedCornerShape(16.dp)
                        )
                        .clickable{

                            playItem(
                                item,
                                false
                            )
                        }
                    ,
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(item.coverImageResourceId),
                        contentDescription = "Image Cover",
                        modifier = Modifier.fillMaxSize(),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
                    )


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        Color.Transparent,
                                        Color.Black,
                                    )
                                )
                            )
                    )


                    Row(
                        modifier = Modifier
                            .align(alignment = Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(
                            modifier = Modifier.weight(5f),
                            text = item.title,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )


                    }
                }

            }
        }

}



