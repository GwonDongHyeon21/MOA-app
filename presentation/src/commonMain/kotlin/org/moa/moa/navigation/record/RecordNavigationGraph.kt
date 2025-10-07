package org.moa.moa.navigation.record

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.presentation.record.textimage.RecordScreen
import org.moa.moa.presentation.record.recorder.RecorderScreen

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
    composable(RecordNavigationItem.Recorder.route) {
        RecorderScreen(
            onBack = { navController.popBackStack() }
        )
    }
}