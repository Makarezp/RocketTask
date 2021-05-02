package com.task.spacex.repository.domain

import java.time.ZonedDateTime

data class LaunchDomain(
    val id: String,
    val missionName: String,
    val patchURL: String?,
    val zonedDateTime: ZonedDateTime,
    val success: Boolean,
    val upcoming: Boolean
)
