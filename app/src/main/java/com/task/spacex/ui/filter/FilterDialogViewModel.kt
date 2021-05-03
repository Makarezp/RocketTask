package com.task.spacex.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.spacex.repository.FilterRepository
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.repository.domain.FilterDomain.SortOrder
import com.task.spacex.repository.domain.FilterDomain.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class FilterDialogViewModel @Inject constructor(
    private val filterRepository: FilterRepository
) : ViewModel() {

    private var _currentFilter = filterRepository.getFilter().value

    private val _dismissAction = MutableSharedFlow<Unit>()
    var dismissAction = _dismissAction.asSharedFlow()

    // These fields are only used only on view initialisation
    // let's keep it simple for now - IMO there's no need to expose it as reactive streams

    // Simplification - view shouldn't be coupled with domain
    val currentFilter: FilterDomain
        get() = _currentFilter

    val minToMaxYears: Pair<Int, Int>
        get() = 2006 to LocalDate.now().year

    val isYearFilterEnabled: Boolean
        get() = _currentFilter.year != null

    val selectedYear: Int
        get() = _currentFilter.year ?: minToMaxYears.first

    fun successStatusChecked() {
        _currentFilter = _currentFilter.copy(status = Status.Success)
    }

    fun failureStatusChecked() {
        _currentFilter = _currentFilter.copy(status = Status.Failure)
    }

    fun allStatusChecked() {
        _currentFilter = _currentFilter.copy(status = Status.All)
    }

    fun dateAscChecked() {
        _currentFilter = _currentFilter.copy(dateSortOrder = SortOrder.Ascending)
    }

    fun dateDescChecked() {
        _currentFilter = _currentFilter.copy(dateSortOrder = SortOrder.Descending)
    }

    fun yearCheckBoxClicked(isChecked: Boolean, currentYear: Int) {
        _currentFilter = if (isChecked) {
            _currentFilter.copy(year = currentYear)
        } else {
            _currentFilter.copy(year = null)
        }
    }

    fun onYearValueChanged(value: Int) {
        _currentFilter = currentFilter.copy(year = value)
    }

    fun applyChanges() {
        filterRepository.setFilter(_currentFilter)
        viewModelScope.launch {
            _dismissAction.emit(Unit)
        }
    }
}
