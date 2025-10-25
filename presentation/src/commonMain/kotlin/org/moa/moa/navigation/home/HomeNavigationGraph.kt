package org.moa.moa.navigation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.moa.moa.presentation.home.detail.HomeDetailScreen
import org.moa.moa.presentation.home.home.HomeScreen
import org.moa.moa.presentation.home.diary.HomeDiaryScreen

fun NavGraphBuilder.homeNavigationGraph(navController: NavController) {
    composable(HomeNavigationItem.Home.route) {
        HomeScreen(
            onNavigateToHomeDiary = {
                navController.navigate(HomeNavigationItem.HomeDiary.route) {
                    popUpTo(HomeNavigationItem.Home.route) { inclusive = true }
                }
            },
            onNavigateToHomeDetail = { navController.navigate(HomeDetail(it)) }
        )
    }
    composable(HomeNavigationItem.HomeDiary.route) {
        HomeDiaryScreen()
    }
    composable<HomeDetail> { backStackEntry ->
        val arguments = backStackEntry.toRoute<HomeDetail>()
        HomeDetailScreen(
            recordNumber = arguments.recordNumber,
            onBack = { navController.popBackStack() }
        )
    }
}