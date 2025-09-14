package org.moa.moa.navigation.user

sealed class UserNavigationItem(val route: String) {
    data object User : UserNavigationItem("user")
}

