package com.example.todayonly

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun formatDate(day: Long): String {
    val date = LocalDate.ofEpochDay(day)

    val formatter = DateTimeFormatter.ofPattern(
        "MMM dd, yyyy"
    )

    return date.format(formatter)
}

fun formatPickedTime(time: LocalTime): String {
    return time.format(
        DateTimeFormatter.ofPattern("h:mm a")
    )
}