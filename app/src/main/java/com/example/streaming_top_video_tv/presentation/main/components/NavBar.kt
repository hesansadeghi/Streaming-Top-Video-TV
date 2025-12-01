package com.example.streaming_top_video_tv.presentation.main.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import com.example.streaming_top_video_tv.models.NavigationItem
import com.example.streaming_top_video_tv.presentation.ui.theme.EbonyClay
import com.example.streaming_top_video_tv.presentation.ui.theme.Platinum


@Composable
fun NavBar(
    navController: NavHostController,
    selectedMenu: String,
    isSelectedMenu: (String) -> Unit
) {

    val menu = listOf(
        NavigationItem.Search,
        NavigationItem.Home,
        NavigationItem.Favorite,
        NavigationItem.Downloads,
    )

    var focusedMenu by rememberSaveable {

        mutableStateOf(NavigationItem.Search.route)
    }


    NavigationRail(
        containerColor = EbonyClay,
        modifier = Modifier
            .fillMaxHeight()
            .onFocusChanged { focusState ->

                if (focusState.hasFocus.not()) {

                    focusedMenu = ""
                }
            }
    ) {

        menu.forEach { navigationItem ->

            val route = navigationItem.route

            NavigationRailItem(
                modifier = Modifier
                    .border(
                        3.dp,
                        if (focusedMenu == route) {
                            Color.Red
                        } else {
                            EbonyClay
                        }, RoundedCornerShape(15.dp)
                    )
                    .onFocusChanged { focusState ->

                        if (focusState.hasFocus) {

                            focusedMenu = route
                        }
                    },
                selected = selectedMenu == route,
                onClick = {
                    isSelectedMenu(route)
//                    selectedMenu = index
                    navController.navigate(route)
                },
                icon = {

                    Icon(
                        painterResource(navigationItem.icon),
                        contentDescription = navigationItem.title,
                        modifier = Modifier.size(
                            if (focusedMenu == route) {
                                30.dp
                            } else {
                                25.dp
                            }
                        ),
                        tint = if (focusedMenu == route) {
                            Color.Red
                        } else {
                            Color.Black
                        }
                    )
                },
                label = {

                    Text(text = navigationItem.title)
                },
                alwaysShowLabel = false,
                colors = NavigationRailItemDefaults.colors(
                    selectedTextColor = Color.White,
                    unselectedTextColor = Platinum,
                    selectedIconColor = Color.White,
                    unselectedIconColor = Platinum,
                    indicatorColor = Color.Transparent
                )

            )

        }

    }


}


@Preview(showBackground = true)
@Composable
fun Preview() {
    NavBar(rememberNavController(), NavigationItem.Home.route) {}
}