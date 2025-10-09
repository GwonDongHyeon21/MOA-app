package org.moa.moa.navigation.calendar

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.presentation.calendar.CalendarScreen
import org.moa.moa.presentation.calendar.detail.CalendarDetailScreen

fun NavGraphBuilder.calendarNavigationGraph(navController: NavController) {
    composable(CalendarNavigationItem.Calendar.route) {
        CalendarScreen(
            onNavigateToDetail = { navController.navigate(CalendarNavigationItem.CalendarDetail.route) },
            onBack = { navController.popBackStack() }
        )
    }
    composable(CalendarNavigationItem.CalendarDetail.route){
        CalendarDetailScreen()
    }
}