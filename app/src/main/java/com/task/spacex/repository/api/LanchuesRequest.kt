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
        val success: Boolean?
    )

    data class Options(
        @Json(name = "limit")
        val limit: Int,
        @Json(name = "page")
        val page: Int
    )

}
