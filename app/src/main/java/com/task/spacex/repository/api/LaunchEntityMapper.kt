package com.task.spacex.repository.api

import com.task.spacex.repository.db.LaunchEntity
import javax.inject.Inject

class LaunchEntityMapper @Inject constructor() {

    fun map(docs: List<LaunchResponse.Doc>): List<LaunchEntity> {
        return docs.map { LaunchEntity(it.id, it.name, it.links.patch.small, it.dateLocal) }
    }
}
