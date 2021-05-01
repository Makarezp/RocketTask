package com.task.spacex.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeMoshiAdapter {

    @ToJson
    fun toJson(value: ZonedDateTime): String {
        return FORMATTER.format(value)
    }

    @FromJson
    fun fromJson(value: String): ZonedDateTime {
        return ZonedDateTime.from(FORMATTER.parse(value))
    }

    companion object {
        private val FORMATTER = DateTimeFormatter.ISO_DATE_TIME
    }
}
