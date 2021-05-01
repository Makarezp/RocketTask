package com.task.spacex

import com.flextrade.kfixture.KFixture
import io.mockk.MockKAnnotations
import org.junit.Before


abstract class UnitTestBase<T: Any> {

    lateinit var sut: T

    val fixture = KFixture()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        sut = initSut()
    }

    abstract fun initSut() : T

}
