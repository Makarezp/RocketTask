package com.task.spacex.ui.launch_list

import androidx.paging.PagingData
import androidx.paging.map
import com.flextrade.jfixture.annotations.Fixture
import com.task.spacex.R
import com.task.spacex.UnitTestBase
import com.task.spacex.repository.CompanyRepository
import com.task.spacex.repository.FilterRepository
import com.task.spacex.repository.LaunchRepository
import com.task.spacex.repository.domain.CompanyDomain
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.domain.LaunchDomain
import com.task.spacex.util.StringsWrapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import java.io.IOException

class RocketListViewModelTest : UnitTestBase<RocketListViewModel>() {

    @MockK
    private lateinit var mockLaunchRepository: LaunchRepository

    @MockK
    private lateinit var mockFilterRepository: FilterRepository

    @MockK
    private lateinit var mockUiMapper: LaunchItemUiMapper

    @MockK
    private lateinit var mockCompanyItemUiMapper: CompanyItemUiMapper

    @MockK
    private lateinit var mockCompanyRepository: CompanyRepository

    @MockK
    lateinit var mockStrings: StringsWrapper

    @Fixture
    lateinit var fixtCompanyString: String

    private var dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    override fun before() {
        Dispatchers.setMain(dispatcher)
        every { mockStrings.resolve(R.string.company) } returns fixtCompanyString
        coEvery { mockCompanyRepository.getCompany() } returns fixture()
        every { mockCompanyItemUiMapper.map(any()) } returns fixture()
    }

    override fun buildSut(): RocketListViewModel {
        return RocketListViewModel(
            mockLaunchRepository,
            mockFilterRepository,
            mockUiMapper,
            mockCompanyItemUiMapper,
            mockCompanyRepository,
            mockStrings
        )
    }

    override fun customiseFixture() {
        fixture.jFixture.customise()
            .sameInstance(FilterDomain.Status::class.java, FilterDomain.Status.All)
    }

    @After
    fun cleanDispatcher() {
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getLaunches emits when filtering changes`() = runBlockingTest {
        val fixFilterDomain1: FilterDomain = fixture()
        val fixFilterDomain2: FilterDomain = fixFilterDomain1
        val fixFilterDomain3: FilterDomain =
            fixFilterDomain1.copy(status = FilterDomain.Status.Failure)

        val launchDomain1: LaunchDomain = fixture()
        val launchDomain2: LaunchDomain = fixture()

        val fixtData1: PagingData<LaunchDomain> = PagingData.from(
            listOf(
                launchDomain1,
                launchDomain2
            )
        )

        val fixtData2: PagingData<LaunchDomain> = PagingData.from(
            listOf(
                fixture(),
                fixture()
            )
        )

        fixtData1.map {
            print(it)
        }

        val filterFlow = MutableStateFlow(fixFilterDomain1)
        every { mockFilterRepository.getFilter() } returns filterFlow
        every { mockLaunchRepository.getLaunches(fixFilterDomain1) } returns flowOf(fixtData1)
        every { mockLaunchRepository.getLaunches(fixFilterDomain3) } returns flowOf(fixtData2)

        val actualData: MutableList<PagingData<PaginetedCell>> = mutableListOf()

        val job = launch {
            sut.getPaginatedLaunches().collect {
                actualData += it
            }
        }

        filterFlow.emit(fixFilterDomain2)
        filterFlow.emit(fixFilterDomain3)

        assert(actualData.size == 2)

        verify(exactly = 1) {
            mockLaunchRepository.getLaunches(fixFilterDomain1)
            mockLaunchRepository.getLaunches(fixFilterDomain3)
        }

        job.cancel()
    }

    @Test
    fun `companyInfo error`() = dispatcher.runBlockingTest {
        val fixtCompany: CompanyDomain = fixture()
        val fixtTextCell: TextCell = fixture()

        coEvery { mockCompanyRepository.getCompany() } returns fixtCompany
        every { mockCompanyItemUiMapper.map(fixtCompany) } returns fixtTextCell

        dispatcher.pauseDispatcher {
            val actual: MutableList<List<CellUiModel>> = mutableListOf()
            sut = buildSut()
            actual += sut.companyInfoItems.value
            val job = launch {
                sut.companyInfoItems.collect {
                    actual += it
                }
            }
            dispatcher.runCurrent()

            assertEquals(2, actual.size)
            assertEquals(initialCompanyInfoState(), actual[0])
            assertEquals(loadedCompanyInfoState(fixtTextCell.text), actual[1])
            job.cancel()
        }
    }

    @Test
    fun `companyInfo success`() = dispatcher.runBlockingTest {

        coEvery { mockCompanyRepository.getCompany() } throws IOException()

        dispatcher.pauseDispatcher {
            val actual: MutableList<List<CellUiModel>> = mutableListOf()
            sut = buildSut()
            actual += sut.companyInfoItems.value
            val job = launch {
                sut.companyInfoItems.collect {
                    actual += it
                }
            }
            dispatcher.runCurrent()

            assertEquals(2, actual.size)
            assertEquals(initialCompanyInfoState(), actual[0])
            assertEquals(emptyList<CellUiModel>(), actual[1])
            job.cancel()
        }
    }

    private fun initialCompanyInfoState(): List<CellUiModel> =
        listOf(SeparatorCell(fixtCompanyString), LoadingCell)

    private fun loadedCompanyInfoState(companyText: String): List<CellUiModel> =
        listOf(SeparatorCell(fixtCompanyString), TextCell(companyText))

}
