package com.task.spacex.ui.launch_list


import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.RequestManager
import com.task.spacex.ui.launch_list.RocketListViewModel.LaunchItemUiModel
import javax.inject.Inject

class LaunchAdapter @Inject constructor(
    private val requestManager: RequestManager
) : PagingDataAdapter<LaunchItemUiModel, LaunchViewHolder>(LAUNCH_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LaunchViewHolder.create(parent, requestManager)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {

        val LAUNCH_COMPARATOR = object : DiffUtil.ItemCallback<LaunchItemUiModel>() {
            override fun areContentsTheSame(oldItem: LaunchItemUiModel, newItem: LaunchItemUiModel): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: LaunchItemUiModel, newItem: LaunchItemUiModel): Boolean =
                oldItem.domain.id == newItem.domain.id
        }
    }
}
