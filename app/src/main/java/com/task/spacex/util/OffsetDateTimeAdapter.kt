package com.task.spacex.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object OffsetDateTimeConverter {

    private val FORMATTER = DateTimeFormatter.ISO_DATE_TIME

    fun toString(value: OffsetDateTime): String {
        return FORMATTER.format(value)
    }

    fun fromString(value: String): OffsetDateTime {
        return OffsetDateTime.from(FORMATTER.parse(value))
    }
}

class MoshiOffsetDateTimeAdapter {

    @ToJson
    fun toJson(value: OffsetDateTime): String {
        return OffsetDateTimeConverter.toString(value)
    }

    @FromJson
    fun fromJson(value: String): OffsetDateTime {
        return OffsetDateTimeConverter.fromString(value)
    }
}
