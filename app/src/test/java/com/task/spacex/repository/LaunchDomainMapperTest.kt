package com.task.spacex.repository

import com.task.spacex.UnitTestBase
import com.task.spacex.repository.db.LaunchEntity
import org.junit.Test

class LaunchDomainMapperTest : UnitTestBase<LaunchDomainMapper>() {

    override fun buildSut(): LaunchDomainMapper {
        return LaunchDomainMapper()
    }

    @Test
    fun map() {
        val fixtEntity: LaunchEntity = fixture()

        val actual = sut.map(fixtEntity)

        assert(fixtEntity.id == actual.id)
        assert(fixtEntity.rocket == actual.rocket)
        assert(fixtEntity.patchUrl == actual.patchURL)
    }
}
