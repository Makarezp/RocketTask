package com.task.spacex.ui.action_sheet

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.task.spacex.databinding.LaunchActionSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
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
        setupClicks()
        observeActions()
    }

    private fun setupClicks() {
        binding.articleRow.setOnClickListener {
            viewModel.onArticleClicked()
        }
        binding.videoRow.setOnClickListener {
            viewModel.onVideoClicked()
        }
        binding.wikiRow.setOnClickListener {
            viewModel.onWikiClicked()
        }
    }

    private fun observeActions() {
        lifecycleScope.launchWhenResumed {
            viewModel.openLinkAction.collectLatest { link ->
                openLink(link)
                dismiss()
            }
        }
    }

    private fun openLink(link: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        try {
            startActivity(appIntent)
        } catch (e: ActivityNotFoundException) {
            Timber.d(e)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
