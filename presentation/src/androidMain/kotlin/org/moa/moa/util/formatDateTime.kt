package org.moa.moa.util

import kotlinx.datetime.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.LocalDateTime as JvmLocalDateTime

actual fun formatDateTime(
    dateTime: LocalDateTime,
    pattern: String,
): String {
    val jvmDateTime = JvmLocalDateTime.of(
        dateTime.year,
        dateTime.monthNumber,
        dateTime.dayOfMonth,
        dateTime.hour,
        dateTime.minute,
        dateTime.second,
        dateTime.nanosecond
    )
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return jvmDateTime.format(formatter)
}