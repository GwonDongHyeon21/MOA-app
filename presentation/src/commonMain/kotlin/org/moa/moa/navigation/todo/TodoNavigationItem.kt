package org.moa.moa.navigation.todo

sealed class TodoNavigationItem(val route: String) {
    data object Todo : TodoNavigationItem("todo")
}

