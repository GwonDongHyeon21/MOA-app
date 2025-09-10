package org.moa.moa.navigation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.presentation.home.HomeScreen

fun NavGraphBuilder.homeNavigationGraph(navController: NavController) {
    composable(HomeNavigationItem.Home.route) {
        HomeScreen()
    }
}