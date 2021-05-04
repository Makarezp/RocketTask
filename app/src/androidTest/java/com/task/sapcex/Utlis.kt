package com.task.sapcex

import android.content.Context
import androidx.annotation.StringRes
import androidx.test.core.app.ApplicationProvider

fun getStringRes(@StringRes stringId: Int, vararg formatArg: Any): String =
    ApplicationProvider.getApplicationContext<Context>().getString(stringId, *formatArg)

