package com.task.spacex.util

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringsWrapper @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    fun resolve(@StringRes stringId: Int, vararg formatArg: Any): String =
        context.getString(stringId, *formatArg)


    fun resolve(@StringRes stringId: Int): String =
        context.getString(stringId)

}
