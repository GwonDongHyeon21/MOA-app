package org.moa.moa.presentation.util

import kotlinx.datetime.LocalDateTime

expect fun formatDateTime(
    dateTime: LocalDateTime,
    pattern: String,
): String