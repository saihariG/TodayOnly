package com.example.todayonly

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatDate(day: Long): String {
    val date = LocalDate.ofEpochDay(day)

    val formatter = DateTimeFormatter.ofPattern(
        "MMM dd, yyyy"
    )

    return date.format(formatter)
}