package com.task.spacex.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.task.spacex.R
import com.task.spacex.databinding.FilterDialogBinding
import com.task.spacex.repository.domain.FilterDomain
import com.task.spacex.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FilterDialog : DialogFragment() {

    private var _binding: FilterDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FilterDialogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDismissAction()
        initStatusRadios(viewModel.currentFilter.status)
        setStatusRadiosClicks()
        setApplyButtonClicks()
        initDateSortRadios(viewModel.currentFilter.dateSortOrder)
        setDateSortRadioClicks()
    }

    private fun observeDismissAction() {
        lifecycleScope.launchWhenStarted {
            viewModel.dismissAction.collect {
                dismiss()
            }
        }
    }

    private fun setApplyButtonClicks() {
        binding.applyButton.setOnClickListener {
            viewModel.applyChanges()
        }
    }

    private fun setStatusRadiosClicks() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.all_radio_button -> viewModel.allStatusChecked()
                R.id.success_radio_button -> viewModel.successStatusChecked()
                R.id.failure_radio_button -> viewModel.failureStatusChecked()
            }
        }
    }

    private fun initStatusRadios(status: FilterDomain.Status) {
        when (status) {
            is FilterDomain.Status.All -> binding.allRadioButton.isChecked = true
            is FilterDomain.Status.Success -> binding.successRadioButton.isChecked = true
            is FilterDomain.Status.Failure -> binding.failureRadioButton.isChecked = true
        }.exhaustive
    }

    private fun initDateSortRadios(order: FilterDomain.SortOrder) {
        when (order) {
            FilterDomain.SortOrder.Ascending -> binding.ascendingRadio.isChecked = true
            FilterDomain.SortOrder.Descending -> binding.descengingRadio.isChecked = true
        }
    }

    private fun setDateSortRadioClicks() {
        binding.dateSortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.ascending_radio -> viewModel.dateAscChecked()
                R.id.descenging_radio -> viewModel.dateDescChecked()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
