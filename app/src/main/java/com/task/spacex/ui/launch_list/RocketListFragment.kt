package com.task.spacex.ui.launch_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.task.spacex.databinding.RocketListFragmentBinding
import com.task.spacex.ui.action_sheet.LaunchActionSheet
import com.task.spacex.ui.filter.FilterDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

@AndroidEntryPoint
class RocketListFragment : Fragment() {

    private var _binding: RocketListFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var glide: RequestManager

    lateinit var adapter: LaunchAdapter

    private val viewModel by viewModels<RocketListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RocketListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initFilterButton()
        observeActions()
    }

    private fun initFilterButton() {
        binding.filterButton.setOnClickListener {
            FilterDialog().show(parentFragmentManager, FilterDialog::class.simpleName)
        }
    }

    private fun initRecycler() {
        adapter = LaunchAdapter(glide, viewModel)
        binding.recycler.adapter = adapter.withLoadStateFooter(LoadStateAdapter())
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.setHasFixedSize(true)
        lifecycleScope.launchWhenCreated {
            viewModel.getLaunches().collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collectLatest { binding.recycler.scrollToPosition(0) }
        }
    }

    private fun observeActions() {
        lifecycleScope.launchWhenCreated {
            viewModel.openSheetAction.collectLatest { launchId ->
                LaunchActionSheet.newInstance(launchId)
                    .show(parentFragmentManager, LaunchActionSheet::class.simpleName)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
