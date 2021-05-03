package com.task.spacex.repository.domain

data class FilterDomain(val status: Status, val dateSortOrder: SortOrder, val year: Int?) {

    sealed class Status {
        object Success : Status()
        object Failure : Status()
        object All : Status()
    }

    sealed class SortOrder {
        object Ascending : SortOrder()
        object Descending : SortOrder()
    }

    companion object {
        val DEFAULT_FILTER_VALUES = FilterDomain(Status.All, SortOrder.Ascending, null)
    }
}
