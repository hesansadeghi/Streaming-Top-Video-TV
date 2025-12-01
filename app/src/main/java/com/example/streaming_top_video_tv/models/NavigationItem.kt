package com.example.streaming_top_video_tv.models

import com.example.streaming_top_video_tv.R
import com.example.streaming_top_video_tv.util.Constants.DOWNLOADS
import com.example.streaming_top_video_tv.util.Constants.FAVORITE
import com.example.streaming_top_video_tv.util.Constants.HOME
import com.example.streaming_top_video_tv.util.Constants.SEARCH

sealed class NavigationItem(var route: String, var title: String, var icon: Int) {

    data object Search : NavigationItem(SEARCH, "Search", R.drawable.outline_video_search_24)

    data object Home : NavigationItem(HOME, "Home", R.drawable.icons8_home)
    data object Favorite : NavigationItem(FAVORITE, "Favorites", R.drawable.baseline_favorite_24)
    data object Downloads : NavigationItem(DOWNLOADS, "Downloads", R.drawable.outline_download_24)

}