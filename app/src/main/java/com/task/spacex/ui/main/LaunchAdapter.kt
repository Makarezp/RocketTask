package com.task.spacex.ui.main


import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.task.spacex.repository.LaunchDomain

/**
 * A simple adapter implementation that shows Reddit posts.
 */
class LaunchAdapter() : PagingDataAdapter<LaunchDomain, LaunchViewHolder>(LAUNCH_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LaunchViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.bind(repoItem)
        }
    }

    companion object {

        val LAUNCH_COMPARATOR = object : DiffUtil.ItemCallback<LaunchDomain>() {
            override fun areContentsTheSame(oldItem: LaunchDomain, newItem: LaunchDomain): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: LaunchDomain, newItem: LaunchDomain): Boolean =
                oldItem.id == newItem.id


        }

    }
}
