package com.crystal.todayprice.viewholder

import com.crystal.todayprice.component.OnItemListItemListener
import com.crystal.todayprice.data.News
import com.crystal.todayprice.databinding.ItemNewsBinding

class NewsViewHolder(
    binding: ItemNewsBinding,
    private val onItemClickListener: OnItemListItemListener
): ListItemViewHolder<ItemNewsBinding>(binding) {
    init {
        binding.root.setOnClickListener {
            item?.let {
                onItemClickListener.onItemClick(it as News)
            }
        }
    }
}