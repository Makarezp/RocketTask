package com.task.spacex.ui.launch_list

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.task.spacex.R
import com.task.spacex.databinding.LaunchItemBinding
import com.task.spacex.ui.launch_list.RocketListViewModel.LaunchItemUiModel

class LaunchViewHolder(
    view: View,
    private val viewModel: RocketListViewModel,
    private val glide: RequestManager,
) : RecyclerView.ViewHolder(view) {
    private val binding = LaunchItemBinding.bind(view)

    fun bind(uiModel: LaunchItemUiModel) {
        loadImage(uiModel.missionIconUrl)
        bindViews(uiModel)
        setUpClicks(uiModel)
    }

    private fun setUpClicks(uiModel: LaunchItemUiModel) {
        binding.root.setOnClickListener {
            viewModel.itemClicked(uiModel.id)
        }
    }

    private fun loadImage(url: String?) {
        glide.load(url)
            .placeholder(ColorDrawable(Color.LTGRAY))
            .into(binding.patch)

    }

    private fun bindViews(uiModel: LaunchItemUiModel) {
        binding.missionText.text = uiModel.missionName
        binding.dateText.text = uiModel.dateAtTime
        binding.daysText.text = uiModel.daysCount
        binding.daysLabelText.text = uiModel.daysToSince
        binding.statusIcon.setImageResource(uiModel.statusIcon)
    }


    companion object {
        fun create(
            parent: ViewGroup,
            viewModel: RocketListViewModel,
            glide: RequestManager,
        ): LaunchViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.launch_item, parent, false)
            return LaunchViewHolder(view, viewModel, glide)
        }
    }
}
