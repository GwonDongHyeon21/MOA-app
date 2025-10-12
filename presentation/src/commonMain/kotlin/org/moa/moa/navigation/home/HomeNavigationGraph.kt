package org.moa.moa.navigation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.presentation.home.detail.HomeDetailScreen
import org.moa.moa.presentation.home.home.HomeScreen
import org.moa.moa.presentation.home.record.HomeRecordScreen

fun NavGraphBuilder.homeNavigationGraph(navController: NavController) {
    composable(HomeNavigationItem.Home.route) {
        HomeScreen(
            onNavigateToHomeRecord = { navController.navigate(HomeNavigationItem.HomeRecord.route) },
            onNavigateToHomeDetail = { navController.navigate(HomeNavigationItem.HomeDetail.route) }
        )
    }
    composable(HomeNavigationItem.HomeRecord.route) {
        HomeRecordScreen(
            onBack = { navController.popBackStack() }
        )
    }
    composable(HomeNavigationItem.HomeDetail.route) {
        HomeDetailScreen()
    }
}