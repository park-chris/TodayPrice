package com.crystal.todayprice.viewholder

import androidx.core.view.isVisible
import com.crystal.todayprice.adapter.HorizontalListAdapter
import com.crystal.todayprice.component.OnItemListItemListener
import com.crystal.todayprice.data.Horizontal
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.databinding.ItemHorizontalBinding
import com.crystal.todayprice.util.HorizontalSpaceItemDecoration

class HorizontalViewHolder(
    private val binding: ItemHorizontalBinding,
    private val onItemClickListener: OnItemListItemListener
): ListItemViewHolder<ItemHorizontalBinding>(binding) {
    private val adapter = HorizontalListAdapter(onItemClickListener)

    init {
        val itemDecoration = HorizontalSpaceItemDecoration(30)
        binding.listView.addItemDecoration(itemDecoration)
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