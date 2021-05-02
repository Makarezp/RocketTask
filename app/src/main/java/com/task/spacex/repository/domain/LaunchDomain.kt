package com.task.spacex.repository.domain

import java.time.OffsetDateTime

data class LaunchDomain(
    val id: String,
    val missionName: String,
    val patchURL: String?,
    val offsetDateTime: OffsetDateTime,
    val success: Boolean,
    val upcoming: Boolean
)
