package org.moa.moa.navigation.home

sealed class HomeNavigationItem(val route: String) {
    data object Home : HomeNavigationItem("home")
}