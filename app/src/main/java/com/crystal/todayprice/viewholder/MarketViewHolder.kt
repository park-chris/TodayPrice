package com.crystal.todayprice.viewholder

import com.crystal.todayprice.component.OnItemListItemListener
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.MarketItemBinding

class MarketViewHolder(
    binding: MarketItemBinding,
    private val onItemClickListener: OnItemListItemListener
): ListItemViewHolder<MarketItemBinding>(binding) {
    init {
        binding.root.setOnClickListener {
            item?.let {
                onItemClickListener.onItemClick(it as Market)
            }
        }
    }
}