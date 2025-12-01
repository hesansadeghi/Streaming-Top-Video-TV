package com.example.streaming_top_video_tv.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.streaming_top_video_tv.R
import com.example.streaming_top_video_tv.presentation.ui.theme.MidnightDark
import com.example.streaming_top_video_tv.util.Constants.SEARCH


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppView(navController: NavHostController, appBarHeight: Dp) {

    TopAppBar(
        modifier = Modifier
            .background(MidnightDark)
            .height(appBarHeight),
        title = {

            Text(
                text = stringResource(R.string.app_name),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.Transparent,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            subtitleContentColor = Color.White
        ),
        actions = {

            IconButton(onClick = {

                navController.navigate(SEARCH)
            }) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "navigate to Search Screen",
                )
            }

        }
    )


}