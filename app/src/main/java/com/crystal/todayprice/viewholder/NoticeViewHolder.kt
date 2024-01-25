package com.crystal.todayprice.viewholder

import com.crystal.todayprice.component.OnItemListItemListener
import com.crystal.todayprice.data.Notice
import com.crystal.todayprice.databinding.ItemNoticeBinding

class NoticeViewHolder(
    binding: ItemNoticeBinding,
    private val onItemClickListener: OnItemListItemListener
): ListItemViewHolder<ItemNoticeBinding>(binding) {
    init {
        binding.root.setOnClickListener {
            item?.let {
                onItemClickListener.onItemClick(it as Notice)
            }
        }
    }
}