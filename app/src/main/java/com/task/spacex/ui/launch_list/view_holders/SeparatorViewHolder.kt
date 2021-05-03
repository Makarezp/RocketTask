package com.task.spacex.ui.launch_list.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.task.spacex.R
import com.task.spacex.databinding.SeparatorItemBinding
import com.task.spacex.ui.launch_list.SeparatorCell

class SeparatorViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.separator_item, parent, false)
) {
    private val binding = SeparatorItemBinding.bind(itemView)

    fun bind(item: SeparatorCell) {
        binding.label.text = item.text
    }

    fun bindText(@StringRes textId: Int) {
        binding.label.setText(textId)
    }
}
