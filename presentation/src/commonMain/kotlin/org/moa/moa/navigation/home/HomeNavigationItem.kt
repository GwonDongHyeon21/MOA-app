package org.moa.moa.navigation.home

import kotlinx.serialization.Serializable

sealed class HomeNavigationItem(val route: String) {
    data object Home : HomeNavigationItem("home")
    data object HomeRecord : HomeNavigationItem("home_record")
}

@Serializable
data class HomeDetail(val recordNumber: Int)