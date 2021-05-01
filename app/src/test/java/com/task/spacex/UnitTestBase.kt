package com.task.spacex

import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.jfixture.FluentCustomisation
import com.flextrade.jfixture.customisation.Customisation
import com.flextrade.jfixture.customisation.OmitAutoPropertyCustomisation
import com.flextrade.kfixture.KFixture
import io.mockk.MockKAnnotations
import org.junit.Before


abstract class UnitTestBase<T : Any> {

    lateinit var sut: T

    val fixture = KFixture()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        setupFixture()
        sut = buildSut()
        FixtureAnnotations.initFixtures(this, fixture.jFixture)
    }

    open fun setupFixture() {
    }

    abstract fun buildSut(): T

}
