package org.moa.moa.navigation.record

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.presentation.record.RecordScreen

fun NavGraphBuilder.recordNavigationGraph(navController: NavController) {
    composable(RecordNavigationItem.RecordText.route) {
        RecordScreen(
            onBack = { navController.popBackStack() }
        )
    }
    composable(RecordNavigationItem.RecordCamera.route) {
        RecordScreen(
            isCamera = true,
            onBack = { navController.popBackStack() }
        )
    }
}