package com.task.spacex.ui.action_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.task.spacex.databinding.LaunchActionSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchActionSheet : BottomSheetDialogFragment() {

    companion object {

        private const val KEY_LAUNCH_ID = "KEY_LAUNCH_ID"

        fun newInstance(launchId: String): LaunchActionSheet {
            return LaunchActionSheet().apply {
                val bundle = Bundle()
                bundle.putString(KEY_LAUNCH_ID, launchId)
                arguments = bundle
            }
        }
    }

    private var _binding: LaunchActionSheetBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var launchActionsViewModelFactory: LaunchActionsViewModelFactory

    private val launchId: String by lazy {
        arguments?.getString(KEY_LAUNCH_ID) ?: throw IllegalArgumentException("Requires launch Id")
    }

    private val viewModel: LaunchActionsViewModel by viewModels {
        LaunchActionsViewModel.provideFactory(launchActionsViewModelFactory, launchId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LaunchActionSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
