package com.task.spacex.repository

import com.task.spacex.UnitTestBase
import com.task.spacex.repository.db.LaunchEntity
import junit.framework.Assert.assertEquals
import org.junit.Test

class LaunchDomainMapperTest : UnitTestBase<LaunchDomainMapper>() {

    override fun buildSut(): LaunchDomainMapper {
        return LaunchDomainMapper()
    }

    @Test
    fun map() {
        val fixtEntity: LaunchEntity = fixture()

        val actual = sut.map(fixtEntity)

        assertEquals(fixtEntity.id, actual.id)
        assertEquals(fixtEntity.missionName, actual.missionName)
        assertEquals(fixtEntity.patchUrl, actual.patchURL)
        assertEquals(fixtEntity.zonedDateTime, actual.zonedDateTime)
        assertEquals(fixtEntity.success, actual.success)
        assertEquals(fixtEntity.upcoming, actual.upcoming)
    }
}
