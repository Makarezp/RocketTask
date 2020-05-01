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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FilterDialog : DialogFragment() {

    private var _binding: FilterDialogBinding? = null
    private val binding get() = _binding!!

    private val model by viewModels<FilterDialogViewModel>()

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
        setStatusRadioGroup(model.currentFilter.status)
        setRadioGroupClicks()
        setApplyButtonClicks()
    }

    private fun observeDismissAction() {
        lifecycleScope.launchWhenStarted {
            model.dismissAction.collect {
                dismiss()
            }
        }
    }

    private fun setApplyButtonClicks() {
        binding.applyButton.setOnClickListener {
            model.applyChanges()
        }
    }

    private fun setRadioGroupClicks() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.all_radio_button -> model.allStatusChecked()
                R.id.success_radio_button -> model.successStatusChecked()
                R.id.failure_radio_button -> model.failureStatusChecked()
            }
        }
    }

    private fun setStatusRadioGroup(status: FilterDomain.Status) {
        when (status) {
            is FilterDomain.Status.All -> binding.allRadioButton.isChecked = true
            is FilterDomain.Status.Success -> binding.successRadioButton.isChecked = true
            is FilterDomain.Status.Failure -> binding.failureRadioButton.isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
