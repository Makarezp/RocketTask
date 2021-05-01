package com.task.spacex.repository.api

import com.task.spacex.UnitTestBase
import com.task.spacex.repository.db.LaunchEntity
import junit.framework.Assert.assertEquals
import org.junit.Test

class LaunchEntityMapperTest : UnitTestBase<LaunchEntityMapper>() {

    override fun buildSut(): LaunchEntityMapper {
        return LaunchEntityMapper()
    }

    @Test
    fun map() {
        val fixtDoc1: LaunchResponse.Doc = fixture()
        val fixtDocs: List<LaunchResponse.Doc> = listOf(fixtDoc1)

        val actual = sut.map(fixtDocs)

        val expected1 = LaunchEntity(fixtDoc1.id, fixtDoc1.name, fixtDoc1.links.patch.small)
        assertEquals(expected1, actual.first())
    }
}
