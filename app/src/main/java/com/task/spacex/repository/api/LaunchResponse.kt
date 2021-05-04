package com.task.spacex.repository.api


import com.squareup.moshi.Json
import java.time.OffsetDateTime

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
    val nextPage: Int?,
    @Json(name = "offset")
    val offset: Int?,
    @Json(name = "page")
    val page: Int,
    @Json(name = "pagingCounter")
    val pagingCounter: Int,
    @Json(name = "prevPage")
    val prevPage: Int?,
    @Json(name = "totalDocs")
    val totalDocs: Int,
    @Json(name = "totalPages")
    val totalPages: Int,
) {
    data class Doc(
        @Json(name = "id")
        val id: String,
        @Json(name = "date_local")
        val dateLocal: OffsetDateTime,
        @Json(name = "date_precision")
        val datePrecision: String,
        @Json(name = "launchpad")
        val launchpad: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "rocket")
        val rocket: Rocket,
        @Json(name = "success")
        val success: Boolean?,
        @Json(name = "links")
        val links: Links,
        @Json(name = "upcoming")
        val upcoming: Boolean,
    ) {

        data class Links(
            @Json(name = "article")
            val article: String?,
            @Json(name = "patch")
            val patch: Patch,
            @Json(name = "wikipedia")
            val wikipedia: String?,
            @Json(name = "webcast")
            val webcast: String?,
        ) {
            data class Patch(
                @Json(name = "large")
                val large: String?,
                @Json(name = "small")
                val small: String?
            )
        }
    }

    data class Rocket(
        @Json(name = "active")
        val active: Boolean,
        @Json(name = "boosters")
        val boosters: Int,
        @Json(name = "company")
        val company: String,
        @Json(name = "cost_per_launch")
        val costPerLaunch: Int,
        @Json(name = "country")
        val country: String,
        @Json(name = "description")
        val description: String,
        @Json(name = "first_flight")
        val firstFlight: String,
        @Json(name = "id")
        val id: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "stages")
        val stages: Int,
        @Json(name = "success_rate_pct")
        val successRatePct: Int,
        @Json(name = "type")
        val type: String,
        @Json(name = "wikipedia")
        val wikipedia: String
    )

}
