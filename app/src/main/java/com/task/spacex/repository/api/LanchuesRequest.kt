package com.task.spacex.repository.api

import com.squareup.moshi.Json

data class LaunchRequest(
    @Json(name = "query")
    val query: Query,
    @Json(name = "options")
    val options: Options
) {

    data class Query(
        @Json(name = "success")
        val success: Boolean?,
        @Json(name = "date_utc")
        val date_utc: DateUtcParams?
    ) {
        data class DateUtcParams(
            @Json(name = "\$gte")
            val from: Int,
            @Json(name = "\$lte")
            val to: Int
        )
    }

    data class Options(
        @Json(name = "limit")
        val limit: Int,
        @Json(name = "page")
        val page: Int,
        @Json(name = "sort")
        val sort: Sort?,
        @Json(name = "populate")
        val populate: List<String>
    ) {

        companion object {
            const val POPULATE_ROCKET = "rocket"
        }

        data class Sort(
            val date_utc: String
        ) {
            companion object {
                const val SORT_ASCENDING = "asc"
                const val SORT_DESCENDING = "desc"
            }
        }

    }
}
