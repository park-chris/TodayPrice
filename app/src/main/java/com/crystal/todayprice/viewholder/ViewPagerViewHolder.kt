package com.crystal.todayprice.viewholder

import com.crystal.todayprice.adapter.ListItemAdapter
import com.crystal.todayprice.component.OnItemListItemListener
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.data.ViewPager
import com.crystal.todayprice.databinding.ItemViewpagerBinding

class ViewPagerViewHolder(
    binding: ItemViewpagerBinding,
    private val onItemClickListener: OnItemListItemListener
): ListItemViewHolder<ItemViewpagerBinding>(binding) {

    private val adapter = ListItemAdapter(onItemClickListener)

    init {
        binding.viewpager.adapter = adapter
    }

    override fun bind(item: ListItem) {
        super.bind(item)
        item as ViewPager
        adapter.submitList(item.items)
    }

}