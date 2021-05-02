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
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        customiseFixture()
        FixtureAnnotations.initFixtures(this, fixture.jFixture)
        before()
        sut = buildSut()

    }

    open fun customiseFixture() {
    }

    open fun before() {

    }

    abstract fun buildSut(): T

}
