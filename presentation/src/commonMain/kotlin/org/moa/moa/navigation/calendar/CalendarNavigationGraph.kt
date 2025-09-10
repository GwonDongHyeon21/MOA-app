package org.moa.moa.navigation.calendar

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.moa.moa.presentation.calendar.CalendarScreen

fun NavGraphBuilder.calendarNavigationGraph(navController: NavController) {
    composable(CalendarNavigationItem.Calendar.route) {
        CalendarScreen(navController)
    }
}