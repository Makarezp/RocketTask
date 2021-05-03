package com.task.spacex.ui.launch_list.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.recyclerview.widget.RecyclerView
import com.task.spacex.R
import com.task.spacex.databinding.LoadStateItemBinding


class LoadStateViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.load_state_item, parent, false)
) {
    private val binding = LoadStateItemBinding.bind(itemView)
    private val progressBar = binding.progressBar
    private val errorMsg = binding.errorMsg


    fun bind(loadState: LoadState) {
        progressBar.isVisible = loadState is Loading
        errorMsg.isVisible = !(loadState as? Error)?.error?.message.isNullOrBlank()
        errorMsg.text = (loadState as? Error)?.error?.message
    }
}
