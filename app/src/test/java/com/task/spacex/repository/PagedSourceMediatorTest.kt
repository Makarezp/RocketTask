package com.task.spacex.repository

import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.*
import androidx.room.withTransaction
import com.task.spacex.R
import com.task.spacex.UnitTestBase
import com.task.spacex.build
import com.task.spacex.repository.api.*
import com.task.spacex.repository.db.*
import com.task.spacex.repository.domain.FilterDomain
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class PagedSourceMediatorTest : UnitTestBase<PagedSourceMediator>() {

    @MockK
    lateinit var mockDb: SpaceXDatabase

    @MockK
    lateinit var mockLaunchDao: LaunchDao

    @MockK
    lateinit var mockPageKeyDao: PageKeyDao

    @MockK
    lateinit var mockRequestMapper: LaunchRequestMapper

    @MockK
    lateinit var mockApiService: ApiService

    @MockK
    lateinit var mockFilter: FilterDomain

    @MockK
    lateinit var mockEntityMapper: LaunchEntityMapper

    @MockK
    lateinit var mockPagingState: PagingState<Int, LaunchEntity>

    /**
     * Required so that we can mock room's withTransaction
     */
    override fun before() {
        mockkStatic(
            "androidx.room.RoomDatabaseKt"
        )

        val transactionLambda = slot<suspend () -> R>()
        coEvery { mockDb.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }
    }

    override fun buildSut(): PagedSourceMediator {
        return PagedSourceMediator(
            mockDb,
            mockLaunchDao,
            mockPageKeyDao,
            mockApiService,
            mockRequestMapper,
            mockEntityMapper,
            mockFilter
        )
    }

    @Test
    fun `prepend data`() = runBlockingTest {
        val fixtLoadType = LoadType.PREPEND

        val actual = sut.load(fixtLoadType, mockPagingState)

        actual as MediatorResult.Success
        assert(actual.endOfPaginationReached)
    }

    @Test
    fun `append when last key`() = runBlockingTest {
        val fixtLoadType = LoadType.APPEND
        val fixtPageKey: PageKeyEntity =
            fixture.build(PageKeyEntity::class).copy(nextPage = null)

        coEvery {
            mockPageKeyDao.pageKeyById(PagedSourceMediator.LAUNCH_PAGE_ID)
        } returns fixtPageKey


        val actual = sut.load(fixtLoadType, mockPagingState)

        actual as MediatorResult.Success
        assert(actual.endOfPaginationReached)
        coVerify {
            mockPageKeyDao.pageKeyById(PagedSourceMediator.LAUNCH_PAGE_ID)
        }
    }

    @Test
    fun `append next page`() = runBlockingTest {
        val fixtLoadType = LoadType.APPEND
        val fixtPageKey: PageKeyEntity = fixture()
        val fixtResponse: LaunchResponse = fixture()
        val fixtRequest: LaunchRequest = fixture()
        val fixtPagingConfig: PagingConfig = fixture()
        val fixtLaunches: List<LaunchEntity> = listOf(
            fixture(),
            fixture()
        )

        every {
            mockPagingState.config
        } returns fixtPagingConfig

        coEvery {
            mockPageKeyDao.pageKeyById(PagedSourceMediator.LAUNCH_PAGE_ID)
        } returns fixtPageKey

        every {
            mockRequestMapper.map(mockFilter, fixtPagingConfig.pageSize, fixtPageKey.nextPage)
        } returns fixtRequest

        coEvery { mockApiService.getLaunches(fixtRequest) } returns fixtResponse

        every { mockEntityMapper.map(fixtResponse.docs) } returns fixtLaunches

        val actual = sut.load(fixtLoadType, mockPagingState)

        actual as MediatorResult.Success
        assert(!actual.endOfPaginationReached)
        coVerify {
            mockPageKeyDao.insert(
                PageKeyEntity(
                    PagedSourceMediator.LAUNCH_PAGE_ID,
                    fixtResponse.nextPage
                )
            )
            mockLaunchDao.insertAll(fixtLaunches)
        }
    }

    @Test
    fun refresh() = runBlockingTest {
        val fixtLoadType = LoadType.APPEND
        val fixtPageKey: PageKeyEntity = fixture()
        val fixtResponse: LaunchResponse = fixture()
        val fixtRequest: LaunchRequest = fixture()
        val fixtPagingConfig: PagingConfig = fixture()
        val fixtLaunches: List<LaunchEntity> = listOf(
            fixture(),
            fixture()
        )

        every {
            mockPagingState.config
        } returns fixtPagingConfig

        coEvery {
            mockPageKeyDao.pageKeyById(PagedSourceMediator.LAUNCH_PAGE_ID)
        } returns fixtPageKey

        every {
            mockRequestMapper.map(mockFilter, fixtPagingConfig.pageSize, fixtPageKey.nextPage)
        } returns fixtRequest

        coEvery { mockApiService.getLaunches(fixtRequest) } returns fixtResponse

        every { mockEntityMapper.map(fixtResponse.docs) } returns fixtLaunches

        val actual = sut.load(fixtLoadType, mockPagingState)

        actual as MediatorResult.Success
        assert(!actual.endOfPaginationReached)
        coVerify {
            mockPageKeyDao.deleteAll()
            mockLaunchDao.deleteAll()
            mockPageKeyDao.insert(
                PageKeyEntity(
                    PagedSourceMediator.LAUNCH_PAGE_ID,
                    fixtResponse.nextPage
                )
            )
            mockLaunchDao.insertAll(fixtLaunches)
        }
    }

    @Test
    fun `api throws`() = runBlockingTest {
        val fixtLoadType = LoadType.APPEND
        val fixtPageKey: PageKeyEntity = fixture()
        val fixtRequest: LaunchRequest = fixture()
        val fixtPagingConfig: PagingConfig = fixture()

        every {
            mockPagingState.config
        } returns fixtPagingConfig

        coEvery {
            mockPageKeyDao.pageKeyById(PagedSourceMediator.LAUNCH_PAGE_ID)
        } returns fixtPageKey

        every {
            mockRequestMapper.map(mockFilter, fixtPagingConfig.pageSize, fixtPageKey.nextPage)
        } returns fixtRequest

        coEvery { mockApiService.getLaunches(fixtRequest) } throws IOException()

        val actual = sut.load(fixtLoadType, mockPagingState)

        assert(actual is MediatorResult.Error)
    }
}
