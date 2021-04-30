package com.task.spacex.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.spacex.R
import com.task.spacex.databinding.LaunchItemBinding
import com.task.spacex.repository.LaunchDomain

class LaunchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = LaunchItemBinding.bind(view)

    fun bind(launch: LaunchDomain) {
        binding.rocket.text = launch.rocket
    }

    private fun showData(launch: LaunchDomain) {
       
    }

    companion object {
        fun create(parent: ViewGroup): LaunchViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.launch_item, parent, false)
            return LaunchViewHolder(view)
        }
    }
}
