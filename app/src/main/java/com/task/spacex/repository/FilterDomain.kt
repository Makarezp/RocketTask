package com.task.spacex.repository

data class FilterDomain(val status: Status) {

    sealed class Status {
        object Success : Status()
        object Failure : Status()
        object All : Status()
    }

    companion object {
        val DEFAULT_FILTER_VALUES = FilterDomain(Status.All)
    }
}