package com.example.todayonly.domain

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


// Abstraction over Time
// Since Prod code should never call LocalDate.now() or Instant.now() directly.
// Instead we go through the clock interface and easier to write tests
interface Clock {
    fun now(): Instant
    fun today(): LocalDate
    fun zone(): ZoneId
}

class ClockImpl : Clock {

    override fun now(): Instant {
        return Instant.now()
    }

    override fun today(): LocalDate {
        return LocalDate.now(zone())
    }

    override fun zone(): ZoneId {
        return ZoneId.systemDefault()
    }
}