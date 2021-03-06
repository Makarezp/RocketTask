package com.task.spacex.repository.api

import com.task.spacex.repository.db.LaunchEntity
import javax.inject.Inject

class LaunchEntityMapper @Inject constructor() {

    fun map(docs: List<LaunchResponse.Doc>): List<LaunchEntity> {
        return docs.map {
            LaunchEntity(
                it.id,
                it.name,
                it.links.patch.small,
                it.dateLocal,
                it.success,
                it.upcoming,
                it.links.webcast,
                it.links.article,
                it.links.wikipedia,
                LaunchEntity.Rocket(
                    it.rocket.name,
                    "${it.rocket.engines.type} ${it.rocket.engines.version}"
                )
            )
        }
    }
}
