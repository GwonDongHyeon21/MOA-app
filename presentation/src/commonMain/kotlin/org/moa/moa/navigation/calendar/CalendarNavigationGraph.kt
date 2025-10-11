package org.moa.moa.navigation.calendar

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.moa.moa.presentation.calendar.calendar.CalendarScreen
import org.moa.moa.presentation.calendar.detail.CalendarDetailScreen

fun NavGraphBuilder.calendarNavigationGraph(navController: NavController) {
    composable(CalendarNavigationItem.Calendar.route) {
        CalendarScreen(
            onNavigateToDetail = { date -> navController.navigate(CalendarDetailRoute(date)) },
            onBack = { navController.popBackStack() }
        )
    }

    composable<CalendarDetailRoute> { backStackEntry ->
        val arguments = backStackEntry.toRoute<CalendarDetailRoute>()
        CalendarDetailScreen(
            date = arguments.date,
            onBack = { navController.popBackStack() }
        )
    }
}