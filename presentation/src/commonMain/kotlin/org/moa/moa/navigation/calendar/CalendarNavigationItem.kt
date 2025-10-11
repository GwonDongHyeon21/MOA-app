package org.moa.moa.navigation.calendar

import kotlinx.serialization.Serializable

sealed class CalendarNavigationItem(val route: String) {
    data object Calendar : CalendarNavigationItem("calendar")
}

@Serializable
data class CalendarDetailRoute(val date: String)