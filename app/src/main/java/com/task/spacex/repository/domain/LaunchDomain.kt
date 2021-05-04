package com.task.spacex.repository.domain

import java.time.OffsetDateTime

data class LaunchDomain(
    val id: String,
    val missionName: String,
    val patchURL: String?,
    val offsetDateTime: OffsetDateTime,
    val success: Boolean?,
    val upcoming: Boolean,
    val webcast: String?,
    val article: String?,
    val wikipedia: String?,
    val rocket: RocketDomain
) {

    data class RocketDomain(val name: String, val type: String)
}
