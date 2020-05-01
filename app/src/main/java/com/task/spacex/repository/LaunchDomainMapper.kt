package com.task.spacex.repository

import com.task.spacex.repository.db.LaunchEntity
import com.task.spacex.repository.domain.LaunchDomain
import javax.inject.Inject

class LaunchDomainMapper @Inject constructor(){

    fun map(entity: LaunchEntity): LaunchDomain {
        return LaunchDomain(
            entity.id,
            entity.rocket,
            entity.patchUrl,
            entity.zonedDateTime
        )
    }
}
