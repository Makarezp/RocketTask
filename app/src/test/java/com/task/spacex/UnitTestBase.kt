package com.task.spacex

import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.kfixture.KFixture
import io.mockk.MockKAnnotations
import org.junit.Before


abstract class UnitTestBase<T : Any> {

    lateinit var sut: T

    val fixture = KFixture()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        customiseFixture()
        FixtureAnnotations.initFixtures(this, fixture.jFixture)
        beforeBuildSut()
        sut = buildSut()

    }

    open fun customiseFixture() {
    }

    open fun beforeBuildSut() {

    }

    abstract fun buildSut(): T

}
