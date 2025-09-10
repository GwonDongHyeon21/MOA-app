package org.moa.moa.navigation.user

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.presentation.user.UserScreen

fun NavGraphBuilder.userNavigationGraph(navController: NavController) {
    composable(UserNavigationItem.User.route) {
        UserScreen()
    }
}