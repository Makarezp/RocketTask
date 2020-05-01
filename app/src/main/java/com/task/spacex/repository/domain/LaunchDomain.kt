package com.task.spacex.repository.domain

import java.time.ZonedDateTime

data class LaunchDomain(
    val id: String,
    val rocket: String,
    val patchURL: String?,
    val zonedDateTime: ZonedDateTime
)
