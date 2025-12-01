package com.example.streaming_top_video_tv.presentation.home.components

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import com.example.streaming_top_video_tv.R
import com.example.streaming_top_video_tv.data.db.entities.DownloadedMovieEntity
import com.example.streaming_top_video_tv.presentation.home.HomeUiEvent
import com.example.streaming_top_video_tv.presentation.home.HomeViewModel
import com.example.streaming_top_video_tv.presentation.home.StreamingMovie
import com.example.streaming_top_video_tv.presentation.ui.theme.DarkTangerine
import com.example.streaming_top_video_tv.util.Utils.formatFileSize


@OptIn(UnstableApi::class)
@Composable
fun ShowFocusedStreamingMovie(
    showStreamingMovie: StreamingMovie,
    viewModel: HomeViewModel,
    playItem: (StreamingMovie, Boolean) -> Unit,
    downLoadItem: (StreamingMovie) -> Unit,
    cancelDownLoadItem: (String) -> Unit
) {


    val downloads by viewModel.downloads.collectAsState()

//    val movie by remember { mutableStateOf(showStreamingMovie) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp), contentAlignment = Alignment.BottomCenter
    ) {

        val downloadUiModel = downloads[showStreamingMovie.id.toString()]

        Image(
            painter = painterResource(showStreamingMovie.coverImageResourceId),
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
                text = "${showStreamingMovie.title} \n${showStreamingMovie.movieSize}${
                    if (downloadUiModel?.state == Download.STATE_DOWNLOADING || downloadUiModel?.state == Download.STATE_QUEUED) {
                        "\\${formatFileSize(downloadUiModel.bytesDownloaded)}"
                    } else ""
                }",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            when (downloadUiModel?.state) {
                null -> {

                    var isFocused by remember { mutableStateOf(false) }

                    IconButton(
                        modifier = Modifier
                            .onFocusChanged { focusState ->
                                isFocused = focusState.hasFocus
                            }
                            .border(
                                3.dp, if (isFocused) {
                                    Color.Red
                                } else {
                                    Color.Transparent
                                },
                                RoundedCornerShape(100.dp)
                            ), onClick = {
                            downLoadItem(showStreamingMovie)
                        }) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            painter = painterResource(R.drawable.outline_download_24),
                            contentDescription = "Download Movie",
                            tint = Color.White
                        )
                    }
                }

                Download.STATE_DOWNLOADING, Download.STATE_QUEUED -> {

                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = {
                                (downloadUiModel.percentDownloaded / 100f).coerceIn(
                                    0f,
                                    1f
                                )
                            },
                            modifier = Modifier.size(35.dp),
                            color = Color.White,
                            strokeWidth = 3.dp,
                            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                        )
                        Text(
                            text = "${downloadUiModel.percentDownloaded.toInt()}%",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }


                    viewModel.onEvent(
                        HomeUiEvent.CheckDownloadMovieServiceIsRunning(showStreamingMovie)
                    )
                }

                Download.STATE_COMPLETED -> {
                    Icon(
                        painter = painterResource(R.drawable.outline_check_24),
                        contentDescription = "Downloaded",
                        tint = Color.Green,
                        modifier = Modifier.size(35.dp)
                    )


                    val entity = DownloadedMovieEntity(
                        movieId = showStreamingMovie.id.toString(),
                        coverImageResourceId = showStreamingMovie.coverImageResourceId,
                        title = showStreamingMovie.title,
                        streamUrl = showStreamingMovie.streamUrl
                    )


                    viewModel.onEvent(
                        HomeUiEvent.CheckExistAndInsertDownloadedMovie(
                            showStreamingMovie.id.toString(), entity
                        )
                    )


                    Log.e("downloadId", "STATE_COMPLETED   ${showStreamingMovie.id}")
                }

                else -> {

                    var isFocused by remember { mutableStateOf(false) }

                    IconButton(
                        modifier = Modifier
                            .onFocusChanged { focusState ->
                                isFocused = focusState.hasFocus
                            }
                            .border(
                                3.dp, if (isFocused) {
                                    Color.Red
                                } else {
                                    Color.Transparent
                                },
                                RoundedCornerShape(100.dp)
                            ),
                        onClick = {
                            downLoadItem(showStreamingMovie)
                        }) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            painter = painterResource(R.drawable.outline_download_24),
                            contentDescription = "Download Movie",
                            tint = Color.White
                        )
                    }
                }
            }

            var isFocused by remember { mutableStateOf(false) }

            IconButton(
                modifier = Modifier
                    .onFocusChanged { focusState ->
                        isFocused = focusState.hasFocus
                    }
                    .border(
                        3.dp, if (isFocused) {
                            Color.Red
                        } else {
                            Color.Transparent
                        },
                        RoundedCornerShape(100.dp)
                    ),
                onClick = {
                    playItem(
                        showStreamingMovie,
                        (downloadUiModel?.state == Download.STATE_COMPLETED)
                    )
                }) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    painter = painterResource(R.drawable.baseline_play_circle_outline_24),
                    contentDescription = "Play Movie",
                    tint = Color.White
                )
            }

        }

        if (downloadUiModel?.state == Download.STATE_DOWNLOADING || downloadUiModel?.state == Download.STATE_QUEUED) {

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart),
                onClick = {
                    cancelDownLoadItem(downloadUiModel.id)
                }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(R.drawable.outline_cancel_24),
                    contentDescription = "Cancel Download Movie",
                    tint = DarkTangerine
                )
            }

        }

    }

}
