package com.task.spacex.ui.launch_list

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.task.spacex.ui.launch_list.view_holders.LoadStateViewHolder

class PaginationLoadStateAdapter : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            loadState: LoadState
    ): LoadStateViewHolder {
        return LoadStateViewHolder(parent)
    }
}
