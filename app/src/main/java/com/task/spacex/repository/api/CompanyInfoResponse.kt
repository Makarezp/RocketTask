package com.task.spacex.repository.api

import com.squareup.moshi.Json

data class CompanyInfoResponse(
    @Json(name = "ceo")
    val ceo: String,
    @Json(name = "coo")
    val coo: String,
    @Json(name = "cto")
    val cto: String,
    @Json(name = "cto_propulsion")
    val ctoPropulsion: String,
    @Json(name = "employees")
    val employees: Int,
    @Json(name = "founded")
    val founded: Int,
    @Json(name = "founder")
    val founder: String,
    @Json(name = "headquarters")
    val headquarters: Headquarters,
    @Json(name = "id")
    val id: String,
    @Json(name = "launch_sites")
    val launchSites: Int,
    @Json(name = "links")
    val links: Links,
    @Json(name = "name")
    val name: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "test_sites")
    val testSites: Int,
    @Json(name = "valuation")
    val valuation: Long,
    @Json(name = "vehicles")
    val vehicles: Int
) {
    data class Headquarters(
        @Json(name = "address")
        val address: String,
        @Json(name = "city")
        val city: String,
        @Json(name = "state")
        val state: String
    )

    data class Links(
        @Json(name = "elon_twitter")
        val elonTwitter: String,
        @Json(name = "flickr")
        val flickr: String,
        @Json(name = "twitter")
        val twitter: String,
        @Json(name = "website")
        val website: String
    )
}
