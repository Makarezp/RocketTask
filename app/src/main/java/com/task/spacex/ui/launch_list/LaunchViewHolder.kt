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

class LaunchViewHolder(
    view: View, private val glide: RequestManager
) : RecyclerView.ViewHolder(view) {
    private val binding = LaunchItemBinding.bind(view)

    fun bind(uiModel: RocketListViewModel.LaunchItemUiModel) {
        binding.rocket.text = uiModel.missionNameLabel
        glide.load(uiModel.missionIconUrl)
            .placeholder(ColorDrawable(Color.LTGRAY))
            .into(binding.patch)
    }


    companion object {
        fun create(parent: ViewGroup, glide: RequestManager): LaunchViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.launch_item, parent, false)
            return LaunchViewHolder(view, glide)
        }
    }
}
