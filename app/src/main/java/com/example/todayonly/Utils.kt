package com.example.todayonly

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
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

fun formatReminderTime(millis: Long): String {
    val instant = Instant.ofEpochMilli(millis)

    val localTime = instant.atZone(ZoneId.systemDefault()).toLocalTime()

    return localTime.format(DateTimeFormatter.ofPattern("h:mm a"))
}