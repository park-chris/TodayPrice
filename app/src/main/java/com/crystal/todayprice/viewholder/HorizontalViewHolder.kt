package com.crystal.todayprice.viewholder

import androidx.core.view.isVisible
import com.crystal.todayprice.adapter.HorizontalListAdapter
import com.crystal.todayprice.component.OnItemListItemListener
import com.crystal.todayprice.data.Horizontal
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.databinding.HorizontalItemBinding

class HorizontalViewHolder(
    private val binding: HorizontalItemBinding,
    private val onItemClickListener: OnItemListItemListener
): ListItemViewHolder<HorizontalItemBinding>(binding) {
    private val adapter = HorizontalListAdapter(onItemClickListener)

    init {
        binding.listView.adapter = adapter
    }

    override fun bind(item: ListItem) {
        super.bind(item)
        item as Horizontal
        binding.titleTextView.text = item.title
        adapter.submitList(item.items)

        if (item.items.isNotEmpty()) {
            binding.seeMoreTextView.setOnClickListener {
                onItemClickListener.onSeeMoreClick(item.items[0].viewType)
            }
        } else {
            binding.seeMoreTextView.isVisible = false
        }

    }
}