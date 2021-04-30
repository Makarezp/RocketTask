package com.task.spacex.repository


import com.squareup.moshi.Json

data class LaunchResponse(
    @Json(name = "docs")
    val docs: List<Doc>,
    @Json(name = "hasNextPage")
    val hasNextPage: Boolean,
    @Json(name = "hasPrevPage")
    val hasPrevPage: Boolean,
    @Json(name = "limit")
    val limit: Int,
    @Json(name = "nextPage")
    val nextPage: Int,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "page")
    val page: Int,
    @Json(name = "pagingCounter")
    val pagingCounter: Int,
    @Json(name = "prevPage")
    val prevPage: Int?,
    @Json(name = "totalDocs")
    val totalDocs: Int,
    @Json(name = "totalPages")
    val totalPages: Int
) {
    data class Doc(
        @Json(name = "id")
        val id: String,
        @Json(name = "date_local")
        val dateLocal: String,
        @Json(name = "date_precision")
        val datePrecision: String,
        @Json(name = "date_unix")
        val dateUnix: Int,
        @Json(name = "date_utc")
        val dateUtc: String,
        @Json(name = "launchpad")
        val launchpad: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "rocket")
        val rocket: String,
    )
}
