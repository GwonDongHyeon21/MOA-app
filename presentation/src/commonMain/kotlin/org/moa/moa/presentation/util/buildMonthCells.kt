package org.moa.moa.presentation.util

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.moa.moa.presentation.util.CellsDimens.TOTAL_DAY_CELLS

private object CellsDimens {
    const val TOTAL_DAY_CELLS = 42
}

fun <T, R> buildMonthCells(
    year: Int,
    month: Month,
    startOn: DayOfWeek,
    items: List<T>,
    selector: (T) -> String,
    mapper: (LocalDate, List<T>) -> R,
): List<R> {
    val firstDateOfMonth = LocalDate(year, month, 1)
    val shift = dayDistance(startOn, firstDateOfMonth.dayOfWeek)
    val startDate = firstDateOfMonth.minus(DatePeriod(days = shift))

    return (0 until TOTAL_DAY_CELLS).map { offset ->
        val date = startDate.plus(DatePeriod(days = offset))
        val dateItems = items.filter { selector(it) == date.toString() }
        mapper(date, dateItems)
    }
}

fun dayDistance(from: DayOfWeek, to: DayOfWeek): Int {
    val fromDate = from.ordinal
    val toDate = to.ordinal
    return (toDate - fromDate + 7) % 7
}