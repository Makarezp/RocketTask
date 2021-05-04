package com.task.spacex.ui.launch_list.view_holders

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.task.spacex.R
import com.task.spacex.databinding.LaunchItemBinding
import com.task.spacex.ui.launch_list.LaunchCell
import com.task.spacex.ui.launch_list.RocketListViewModel

class LaunchViewHolder(
    parent: ViewGroup,
    private val viewModel: RocketListViewModel,
    private val glide: RequestManager,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.launch_item, parent, false)
) {
    private val binding = LaunchItemBinding.bind(itemView)

    fun bind(uiModel: LaunchCell) {
        loadImage(uiModel.missionIconUrl)
        bindViews(uiModel)
        setUpClicks(uiModel)
    }

    private fun setUpClicks(uiModel: LaunchCell) {
        binding.root.setOnClickListener {
            viewModel.itemClicked(uiModel.id)
        }
    }

    private fun loadImage(url: String?) {
        glide.load(url)
            .placeholder(ColorDrawable(Color.LTGRAY))
            .into(binding.patch)
    }

    private fun bindViews(uiModel: LaunchCell) {
        binding.missionText.text = uiModel.missionName
        binding.dateText.text = uiModel.dateAtTime
        binding.daysText.text = uiModel.daysCount
        binding.rocketText.text = uiModel.rocket
        binding.daysLabelText.text = uiModel.daysToSince
        binding.statusIcon.setImageResource(uiModel.statusIcon)
    }

}
