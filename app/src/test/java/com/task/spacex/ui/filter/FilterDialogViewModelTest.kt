package com.task.spacex.ui.filter

import com.flextrade.jfixture.annotations.Fixture
import com.task.spacex.UnitTestBase
import com.task.spacex.repository.FilterRepository
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.domain.FilterDomain.SortOrder
import com.task.spacex.repository.domain.FilterDomain.Status
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import java.time.LocalDate

class FilterDialogViewModelTest : UnitTestBase<FilterDialogViewModel>() {

    @MockK
    private lateinit var mockFilterRepo: FilterRepository

    @Fixture
    private lateinit var fixtFilter: FilterDomain

    private lateinit var filterFlow: MutableStateFlow<FilterDomain>

    private val fixtYear: Int = fixture.range(IntRange(2006, LocalDate.now().year))

    private var dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    override fun customiseFixture() {
        fixture.jFixture.customise()
            .sameInstance(Status::class.java, Status.Success)
        fixture.jFixture.customise()
            .sameInstance(SortOrder::class.java, SortOrder.Ascending)
    }

    override fun before() {
        Dispatchers.setMain(dispatcher)
        filterFlow = MutableStateFlow(fixtFilter)
        every { mockFilterRepo.getFilter() } returns filterFlow
    }

    override fun buildSut(): FilterDialogViewModel {
        return FilterDialogViewModel(mockFilterRepo)
    }

    @After
    fun cleanDispatcher() {
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `current filter is populated from repository`() = runBlockingTest {
        val actual = sut.currentFilter

        assertEquals(fixtFilter, actual)
    }

    @Test
    fun apply() = runBlockingTest {
        var dismiss = false
        val job = launch {
            sut.dismissAction.collect {
                dismiss = true
            }
        }
        sut.applyChanges()
        assert(dismiss)
        verify {
            mockFilterRepo.setFilter(fixtFilter)
        }
        job.cancel()
    }

    @Test
    fun successStatusChecked() {
        sut.successStatusChecked()
        assert(sut.currentFilter == fixtFilter.copy(Status.Success))
    }

    @Test
    fun failureStatusChecked() {
        sut.failureStatusChecked()
        assert(sut.currentFilter == fixtFilter.copy(Status.Failure))
    }

    @Test
    fun allStatusChecked() {
        sut.allStatusChecked()
        assert(sut.currentFilter == fixtFilter.copy(Status.All))
    }

    @Test
    fun dateAscChecked() {
        sut.dateAscChecked()
        assert(sut.currentFilter == fixtFilter.copy(dateSortOrder = SortOrder.Ascending))
    }

    @Test
    fun dateDescChecked() {
        sut.dateDescChecked()
        assert(sut.currentFilter == fixtFilter.copy(dateSortOrder = SortOrder.Descending))
    }

    @Test
    fun minToMax() {
        val actual = sut.minToMaxYears
        val expected = 2006 to LocalDate.now().year

        assertEquals(expected, actual)
    }

    @Test
    fun `year filtering enabled when filter domain value is not null`() {
        fixtFilter = fixtFilter.copy(year = fixtYear)
        filterFlow.value = fixtFilter
        sut = buildSut()
        assertTrue(sut.isYearFilterEnabled)
    }

    @Test
    fun `year filtering disable when filter domain value is null`() {
        fixtFilter = fixtFilter.copy(year = null)
        filterFlow.value = fixtFilter
        sut = buildSut()
        assertFalse(sut.isYearFilterEnabled)
    }

    @Test
    fun `selected year is value of filter`() {
        fixtFilter = fixtFilter.copy(year = fixtYear)
        filterFlow.value = fixtFilter
        sut = buildSut()
        assertEquals(fixtFilter.year, sut.selectedYear)
    }

    @Test
    fun `selected year is min when filter value is null`() {
        fixtFilter = fixtFilter.copy(year = null)
        filterFlow.value = fixtFilter
        sut = buildSut()
        assertEquals(sut.minToMaxYears.first, sut.selectedYear)
    }

    @Test
    fun onYearValueChanged() {
        val fixtYear: Int = fixture()
        sut.onYearValueChanged(fixtYear)
        assertEquals(fixtYear, sut.currentFilter.year)
    }

    @Test
    fun `on checkbox checked`() {
        val fixtYear: Int = fixture()
        sut.yearCheckBoxClicked(true, fixtYear)
        assertEquals(fixtYear, sut.currentFilter.year)
    }

    @Test
    fun `on checkbox unchecked`() {
        val fixtYear: Int = fixture()
        sut.yearCheckBoxClicked(false, fixtYear)
        assertNull(sut.currentFilter.year)
    }
}
