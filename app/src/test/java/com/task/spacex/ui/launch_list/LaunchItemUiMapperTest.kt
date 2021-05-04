package com.task.spacex.ui.launch_list

import com.flextrade.jfixture.annotations.Fixture
import com.task.spacex.R
import com.task.spacex.UnitTestBase
import com.task.spacex.repository.domain.LaunchDomain
import com.task.spacex.util.StringsWrapper
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.OffsetDateTime

class LaunchItemUiMapperTest : UnitTestBase<LaunchItemUiMapper>() {

    @RelaxedMockK
    private lateinit var mockStrings: StringsWrapper

    @Fixture
    private lateinit var fixtLaunch: LaunchDomain

    override fun buildSut(): LaunchItemUiMapper {
        return LaunchItemUiMapper(mockStrings)
    }

    override fun before() {
        every { mockStrings.resolve(R.string.date_format) } returns "yyyy-MM-dd"
        every { mockStrings.resolve(R.string.time_format) } returns "hh:mm a"
    }

    @Test
    fun `direct mapping fields`() {
        val actual = sut.map(fixtLaunch)

        assertEquals(fixtLaunch.id, actual.id)
        assertEquals(fixtLaunch.missionName, actual.missionName)
        assertEquals(fixtLaunch.patchURL, actual.missionIconUrl)
    }

    @Test
    fun `maps success status icon`() {
        fixtLaunch = fixtLaunch.copy(success = true)

        val actual = sut.map(fixtLaunch)

        assertEquals(R.drawable.ic_baseline_success, actual.statusIcon)
    }

    @Test
    fun `maps failure status icon`() {
        fixtLaunch = fixtLaunch.copy(success = false)

        val actual = sut.map(fixtLaunch)

        assertEquals(R.drawable.ic_baseline_fail, actual.statusIcon)
    }

    @Test
    fun `maps upcoming status icon`() {
        fixtLaunch = fixtLaunch.copy(success = null)

        val actual = sut.map(fixtLaunch)

        assertEquals(R.drawable.ic_timer, actual.statusIcon)
    }

    @Test
    fun `map date at time label`() {
        val fixtDate = OffsetDateTime.parse("2020-06-13T05:21:00-04:00")
        val fixtLabel: String = fixture()
        fixtLaunch = fixtLaunch.copy(offsetDateTime = fixtDate)

        every {
            mockStrings.resolve(R.string.date_at_time, "2020-06-13", "05:21 AM")
        } returns fixtLabel

        val actual = sut.map(fixtLaunch)

        assertEquals(fixtLabel, actual.dateAtTime)
    }

    @Test
    fun `map from label`() {
        val fixtLabel: String = fixture()
        fixtLaunch = fixtLaunch.copy(upcoming = true)

        every { mockStrings.resolve(R.string.to_launch) } returns fixtLabel

        val actual = sut.map(fixtLaunch)

        assertEquals(fixtLabel, actual.daysToSince)
    }

    @Test
    fun `map since label`() {
        val fixtLabel: String = fixture()
        fixtLaunch = fixtLaunch.copy(upcoming = false)

        every { mockStrings.resolve(R.string.since_launch) } returns fixtLabel

        val actual = sut.map(fixtLaunch)

        assertEquals(fixtLabel, actual.daysToSince)
    }

    @Test
    fun `map days count label since now`() {
        val fixtDaysSince = fixture.range(LongRange(1, 3000))
        val fixtLaunchDateDate = OffsetDateTime.now().minusDays(fixtDaysSince)
        fixtLaunch = fixtLaunch.copy(upcoming = false, offsetDateTime = fixtLaunchDateDate)

        val actual = sut.map(fixtLaunch)

        assertEquals(fixtDaysSince.toString(), actual.daysCount)
    }

    @Test
    fun `map days count label from now`() {
        val fixtDaysFrom = fixture.range(LongRange(1, 3000))
        // Interesting problem -- we need to add some minimal time do days,
        // plus days is exact -- before code reaches assert it's nominal one day less
        // that's why we're adding one minute
        val fixtLaunchDateDate = OffsetDateTime.now().plusDays(fixtDaysFrom).plusMinutes(1)
        fixtLaunch = fixtLaunch.copy(upcoming = true, offsetDateTime = fixtLaunchDateDate)

        val actual = sut.map(fixtLaunch)

        assertEquals(fixtDaysFrom.toString(), actual.daysCount)
    }

    @Test
    fun `map rocket`() {
        val expected = "${fixtLaunch.rocket.name} / ${fixtLaunch.rocket.type}"

        val actual = sut.map(fixtLaunch)

        assertEquals(expected, actual.rocket)
    }

}
