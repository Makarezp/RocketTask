package com.task.spacex.ui

import com.flextrade.jfixture.annotations.Fixture
import com.task.spacex.UnitTestBase
import com.task.spacex.repository.FilterRepository
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.domain.FilterDomain.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

class FilterDialogViewModelTest : UnitTestBase<FilterDialogViewModel>() {

    @MockK
    private lateinit var mockFilterRepo: FilterRepository

    @Fixture
    private lateinit var fixtFilter: FilterDomain

    private lateinit var filterFlow: MutableStateFlow<FilterDomain>

    private var dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    override fun customiseFixture() {
        fixture.jFixture.customise()
            .sameInstance(Status::class.java, Status.Success)
    }

    override fun beforeBuildSut() {
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
        val job = launch { sut.dismissAction.collect {
            dismiss = true
        } }
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
}
