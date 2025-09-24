package org.moa.moa.util

import kotlinx.datetime.LocalDateTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

actual fun formatDateTime(
    dateTime: LocalDateTime,
    pattern: String
): String {
    val comps = NSDateComponents().apply {
        year = dateTime.year.toLong()
        month = dateTime.monthNumber.toLong()
        day = dateTime.dayOfMonth.toLong()
        hour = dateTime.hour.toLong()
        minute = dateTime.minute.toLong()
        second = dateTime.second.toLong()
    }
    val cal = NSCalendar.currentCalendar
    val nsDate = cal.dateFromComponents(comps) ?: return ""

    val formatter = NSDateFormatter().apply {
        dateFormat = pattern
        locale = NSLocale.currentLocale
    }
    return formatter.stringFromDate(nsDate)
}