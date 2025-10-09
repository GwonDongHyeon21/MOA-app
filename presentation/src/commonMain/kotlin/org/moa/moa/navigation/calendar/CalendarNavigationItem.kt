package org.moa.moa.navigation.calendar

sealed class CalendarNavigationItem(val route: String) {
    data object Calendar : CalendarNavigationItem("calendar")
    data object CalendarDetail : CalendarNavigationItem("calendar_detail")
}