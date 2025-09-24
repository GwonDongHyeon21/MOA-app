package org.moa.moa.util

import kotlinx.datetime.LocalDateTime

expect fun formatDateTime(
    dateTime: LocalDateTime,
    pattern: String,
): String