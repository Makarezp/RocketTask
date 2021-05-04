package com.task.spacex.ui.launch_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
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

    lateinit var topItemsAdapter: ItemAdapter
    lateinit var launchItemsAdapter: PaginatedLaunchAdapter

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
        initSwipeToRefresh()
    }

    private fun initFilterButton() {
        binding.filterButton.setOnClickListener {
            FilterDialog().show(parentFragmentManager, FilterDialog::class.simpleName)
        }
    }

    private fun initRecycler() {
        launchItemsAdapter = PaginatedLaunchAdapter(glide, viewModel)
        val loadStateAdapter = PaginationLoadStateAdapter()

        launchItemsAdapter.addLoadStateListener {
            loadStateAdapter.loadState = it.append
        }
        topItemsAdapter = ItemAdapter()
        val concatAdapter = ConcatAdapter(
            topItemsAdapter,
            launchItemsAdapter,
            loadStateAdapter
        )
        binding.recycler.adapter = concatAdapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.setHasFixedSize(true)
        observeAdapterLoadingState()
        scrollToTopOnLoadFinish()
        observePaginatedItems()
        observeTopItems()
    }

    private fun observeAdapterLoadingState() {
        lifecycleScope.launchWhenCreated {
            launchItemsAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            launchItemsAdapter.refresh()
        }
    }

    private fun observePaginatedItems() {
        lifecycleScope.launchWhenCreated {
            viewModel.getPaginatedLaunches().collectLatest {
                launchItemsAdapter.submitData(it)
            }
        }
    }

    private fun observeTopItems() {
        lifecycleScope.launchWhenCreated {
            viewModel.companyInfoItems.collectLatest {
                topItemsAdapter.submitList(it)
            }
        }
    }

    private fun scrollToTopOnLoadFinish() {
        lifecycleScope.launchWhenCreated {
            launchItemsAdapter.loadStateFlow
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
