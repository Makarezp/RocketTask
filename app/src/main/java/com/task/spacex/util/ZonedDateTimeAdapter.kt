package com.task.spacex.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object ZonedDateTimeConverter {

    private val FORMATTER = DateTimeFormatter.ISO_DATE_TIME

    fun toString(value: ZonedDateTime): String {
        return FORMATTER.format(value)
    }

    fun fromString(value: String): ZonedDateTime {
        return ZonedDateTime.from(FORMATTER.parse(value))
    }
}

class MoshiZonedDateTimeAdapter {

    @ToJson
    fun toJson(value: ZonedDateTime): String {
        return ZonedDateTimeConverter.toString(value)
    }

    @FromJson
    fun fromJson(value: String): ZonedDateTime {
        return ZonedDateTimeConverter.fromString(value)
    }
}
