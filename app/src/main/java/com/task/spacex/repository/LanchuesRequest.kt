package com.task.spacex.repository

import com.squareup.moshi.Json

data class LaunchRequest(
    @Json(name = "options")
    val options: Options
) {
    data class Options(
        @Json(name = "limit")
        val limit: Int
    )

}
