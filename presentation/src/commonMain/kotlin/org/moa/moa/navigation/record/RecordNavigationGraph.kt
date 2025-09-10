package org.moa.moa.navigation.record

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.presentation.record.RecordCameraScreen
import org.moa.moa.presentation.record.RecordRecordScreen
import org.moa.moa.presentation.record.RecordTextScreen

fun NavGraphBuilder.recordNavigationGraph(navController: NavController) {
    composable(RecordNavigationItem.RecordText.route) {
        RecordTextScreen(navController)
    }
    composable(RecordNavigationItem.RecordRecord.route) {
        RecordRecordScreen(navController)
    }
    composable(RecordNavigationItem.RecordCamera.route) {
        RecordCameraScreen(navController)
    }
}