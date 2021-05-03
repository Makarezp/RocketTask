package com.task.spacex.ui.launch_list.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.spacex.R
import com.task.spacex.databinding.TextItemBinding
import com.task.spacex.ui.launch_list.TextCell

class TextViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.text_item, parent, false)
) {

    private val binding = TextItemBinding.bind(itemView)

    fun bind(textCell: TextCell) {
        binding.label.text = textCell.text
    }

}
