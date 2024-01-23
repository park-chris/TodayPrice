package com.crystal.todayprice.viewholder

import com.crystal.todayprice.component.OnItemListItemListener
import com.crystal.todayprice.data.News
import com.crystal.todayprice.databinding.NewsItemBinding

class NewsViewHolder(
    binding: NewsItemBinding,
    private val onItemClickListener: OnItemListItemListener
): ListItemViewHolder<NewsItemBinding>(binding) {
    init {
        binding.root.setOnClickListener {
            item?.let {
                onItemClickListener.onItemClick(it as News)
            }
        }
    }
}