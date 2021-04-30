package com.task.spacex.ui.main


import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.task.spacex.repository.LaunchDomain
import javax.inject.Inject

class LaunchAdapter @Inject constructor(
    private val requestManager: RequestManager
) : PagingDataAdapter<LaunchDomain, LaunchViewHolder>(LAUNCH_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LaunchViewHolder.create(parent, requestManager)
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
