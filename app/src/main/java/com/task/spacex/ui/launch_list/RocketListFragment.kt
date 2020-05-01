package com.task.spacex.ui.launch_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.spacex.databinding.RocketListFragmentBinding
import com.task.spacex.ui.filter.FilterDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class RocketListFragment : Fragment() {

    private var _binding: RocketListFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: LaunchAdapter

    private val model by viewModels<RocketListViewModel>()

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
    }

    private fun initFilterButton() {
        binding.filterButton.setOnClickListener {
            FilterDialog().show(parentFragmentManager, FilterDialog::class.simpleName)
        }
    }

    private fun initRecycler() {
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.setHasFixedSize(true)
        lifecycleScope.launchWhenCreated {
            model.getLaunches().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
