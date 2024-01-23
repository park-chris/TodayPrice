package com.crystal.todayprice.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.crystal.todayprice.BR
import com.crystal.todayprice.data.ListItem

abstract class ListItemViewHolder<VB: ViewDataBinding>(
    private val binding: VB
): RecyclerView.ViewHolder(binding.root) {
    protected var item: ListItem? = null

    open fun bind(item: ListItem) {
        this.item = item
        binding.setVariable(BR.item, this.item)
    }
}