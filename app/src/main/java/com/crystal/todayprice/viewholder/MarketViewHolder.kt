package com.crystal.todayprice.viewholder

import com.crystal.todayprice.util.OnItemListItemListener
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ItemMarketBinding

class MarketViewHolder(
    binding: ItemMarketBinding,
    private val onItemClickListener: OnItemListItemListener
): ListItemViewHolder<ItemMarketBinding>(binding) {
    init {
        binding.root.setOnClickListener {
            item?.let {
                onItemClickListener.onItemClick(it as Market)
            }
        }
    }
}