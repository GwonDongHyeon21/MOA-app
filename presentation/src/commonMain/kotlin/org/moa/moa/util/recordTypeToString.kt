package org.moa.moa.util

import org.moa.moa.presentation.ui.theme.Strings

fun recordTypeToString(recordType: String) = when (recordType) {
    "text+image" -> Strings.record_text_image
    "image" -> Strings.record_image
    "text" -> Strings.record_text
    else -> ""
}