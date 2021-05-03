package com.task.spacex.repository.domain

data class CompanyDomain(
    val name: String,
    val founderName: String,
    val yearFounded: Int,
    val employees: Int,
    val launchSites: Int,
    val valuation: Long
)
