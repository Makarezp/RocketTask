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
import com.task.spacex.repository.domain.LaunchDomain

class LaunchViewHolder(
    view: View, private val glide: RequestManager
) : RecyclerView.ViewHolder(view) {
    private val binding = LaunchItemBinding.bind(view)

    fun bind(launch: LaunchDomain) {
        binding.rocket.text = launch.offsetDateTime.toString()
        glide.load(launch.patchURL)
            .placeholder(ColorDrawable(Color.LTGRAY))
            .into(binding.patch)
    }

    private fun showData(launch: LaunchDomain) {

    }

    companion object {
        fun create(parent: ViewGroup, glide: RequestManager): LaunchViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.launch_item, parent, false)
            return LaunchViewHolder(view, glide)
        }
    }
}
