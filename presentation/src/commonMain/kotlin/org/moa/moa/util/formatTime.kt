package org.moa.moa.util

fun formatRecordTime(ms: Long): String {
    val totalSec = (ms / 1000).coerceAtLeast(0)
    val m = totalSec / 60
    val s = totalSec % 60
    return "${m}:${s.toString().padStart(2, '0')}"
}