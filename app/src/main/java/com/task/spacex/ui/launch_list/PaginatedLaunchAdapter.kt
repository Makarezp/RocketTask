package com.task.spacex.ui.launch_list


import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.task.spacex.R
import com.task.spacex.ui.launch_list.view_holders.LaunchViewHolder
import com.task.spacex.ui.launch_list.view_holders.SeparatorViewHolder
import com.task.spacex.util.exhaustive

class PaginatedLaunchAdapter(
    private val requestManager: RequestManager,
    private val rocketListViewModel: RocketListViewModel,
) : PagingDataAdapter<PaginetedCell, RecyclerView.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LAUNCH -> LaunchViewHolder(parent, rocketListViewModel, requestManager)
            VIEW_TYPE_SEPARATOR -> SeparatorViewHolder(parent)
            else -> throw UnsupportedOperationException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            when (item) {
                is LaunchCell -> (holder as LaunchViewHolder).bind(item)
                is Separator -> (holder as SeparatorViewHolder).bindText(R.string.launches)
            }.exhaustive
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item) {
            is LaunchCell -> VIEW_TYPE_LAUNCH
            is Separator -> VIEW_TYPE_SEPARATOR
            else -> -1
        }
    }

    companion object {

        const val VIEW_TYPE_LAUNCH = 1
        const val VIEW_TYPE_SEPARATOR = 2

        val DIFF_UTIL = object : DiffUtil.ItemCallback<PaginetedCell>() {
            override fun areContentsTheSame(
                oldItem: PaginetedCell,
                newItem: PaginetedCell
            ): Boolean =
                when {
                    oldItem is LaunchCell && newItem is LaunchCell -> oldItem == newItem
                    else -> true
                }

            override fun areItemsTheSame(oldItem: PaginetedCell, newItem: PaginetedCell): Boolean =
                when {
                    oldItem is LaunchCell && newItem is LaunchCell -> oldItem.id == newItem.id
                    else -> true
                }
        }
    }
}
