package com.task.spacex

import com.flextrade.kfixture.KFixture
import kotlin.reflect.KClass

fun <T: Any>KFixture.build(clazz: KClass<T>): T {
    return this.jFixture.create(clazz.javaObjectType)
}
