package com.task.spacex.ui.launch_list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.task.spacex.ui.launch_list.view_holders.LoadStateViewHolder
import com.task.spacex.ui.launch_list.view_holders.SeparatorViewHolder
import com.task.spacex.ui.launch_list.view_holders.TextViewHolder

class ItemAdapter : ListAdapter<CellUiModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SEPARATOR_VIEW_TYPE -> SeparatorViewHolder(parent)
            TEXT_VIEW_TYPE -> TextViewHolder(parent)
            LOADING_VIEW_TYPE -> LoadStateViewHolder(parent)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is TextCell -> (holder as TextViewHolder).bind(item)
            is SeparatorCell -> (holder as SeparatorViewHolder).bind(item)
            else -> {
                // NOOP
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TextCell -> TEXT_VIEW_TYPE
            is LoadingCell -> LOADING_VIEW_TYPE
            is SeparatorCell -> SEPARATOR_VIEW_TYPE
            else -> -1
        }
    }

    companion object {

        private const val SEPARATOR_VIEW_TYPE = 1
        private const val TEXT_VIEW_TYPE = 2
        private const val LOADING_VIEW_TYPE = 3

        val DIFF_UTIL = object : DiffUtil.ItemCallback<CellUiModel>() {
            override fun areContentsTheSame(oldItem: CellUiModel, newItem: CellUiModel): Boolean =
                when {
                    oldItem is LoadingCell && newItem is LoadingCell -> true
                    oldItem is TextCell && newItem is TextCell -> oldItem == newItem
                    oldItem is SeparatorCell && newItem is SeparatorCell -> oldItem == newItem
                    else -> false
                }

            override fun areItemsTheSame(oldItem: CellUiModel, newItem: CellUiModel): Boolean =
                when {
                    oldItem is LoadingCell && newItem is LoadingCell -> true
                    oldItem is TextCell && newItem is TextCell -> oldItem.text == newItem.text
                    oldItem is SeparatorCell && newItem is SeparatorCell -> oldItem.text == newItem.text
                    else -> false
                }
        }
    }
}
