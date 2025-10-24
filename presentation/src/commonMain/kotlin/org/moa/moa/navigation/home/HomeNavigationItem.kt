package org.moa.moa.navigation.home

import kotlinx.serialization.Serializable

sealed class HomeNavigationItem(val route: String) {
    data object Home : HomeNavigationItem("home")
    data object HomeDiary : HomeNavigationItem("home_diary")
}

@Serializable
data class HomeDetail(val recordNumber: Int)