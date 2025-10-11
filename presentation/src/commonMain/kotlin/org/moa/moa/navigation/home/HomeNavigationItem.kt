package org.moa.moa.navigation.home

sealed class HomeNavigationItem(val route: String) {
    data object Home : HomeNavigationItem("home")
    data object HomeRecord : HomeNavigationItem("home_record")
    data object HomeDetail : HomeNavigationItem("home_detail")
}