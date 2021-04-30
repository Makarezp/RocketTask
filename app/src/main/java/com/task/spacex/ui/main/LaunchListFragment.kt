package com.task.spacex.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.task.spacex.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchListFragment : Fragment() {

    companion object {
        fun newInstance() = LaunchListFragment()
    }

    private val viewModel by viewModels<LaunchListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}