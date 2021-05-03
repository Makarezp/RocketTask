package com.task.spacex.ui.launch_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.task.spacex.R
import com.task.spacex.repository.FilterRepository
import com.task.spacex.repository.LaunchRepository
import com.task.spacex.util.StringsWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketListViewModel @Inject constructor(
    private val launchRepository: LaunchRepository,
    private val filterRepository: FilterRepository,
    private val launchItemUiMapper: LaunchItemUiMapper,
    private val strings: StringsWrapper,
) : ViewModel() {

    private val _openSheetAction = MutableSharedFlow<String>()
    var openSheetAction = _openSheetAction.asSharedFlow()

    private val _companyItems: MutableStateFlow<List<CellUiModel>> =
        MutableStateFlow(initialCompanyInfoState())
    val companyInfoItems = _companyItems.asStateFlow()

    init {
        loadCompanyInfo()
    }

    fun getPaginatedLaunches(): Flow<PagingData<PaginetedCell>> =
        filterRepository.getFilter()
            .distinctUntilChanged { old, new -> old == new }
            .flatMapLatest { filterDomain -> launchRepository.getLaunches(filterDomain) }
            .map { data ->
                data.map { launch ->
                    val paginetedCell = launchItemUiMapper.map(launch) as PaginetedCell
                    paginetedCell
                }
            }
            .map { data ->
                data.insertHeaderItem(
                    terminalSeparatorType = TerminalSeparatorType.SOURCE_COMPLETE,
                    item = Separator
                )
            }
            .cachedIn(viewModelScope)


    fun itemClicked(id: String) {
        viewModelScope.launch {
            _openSheetAction.emit(id)
        }
    }

    private fun loadCompanyInfo() {
        viewModelScope.launch {
            _companyItems.emit(successCompanyInfoState())
        }
    }

    private fun initialCompanyInfoState(): List<CellUiModel> =
        listOf(SeparatorCell(strings.resolve(R.string.company)), LoadingCell)


    private fun successCompanyInfoState(): List<CellUiModel> =
        listOf(SeparatorCell(strings.resolve(R.string.company)), TextCell("Test"))

}
