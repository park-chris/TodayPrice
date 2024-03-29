package com.crystal.todayprice.viewholder

import android.view.View
import androidx.core.view.isVisible
import com.crystal.todayprice.adapter.HorizontalListAdapter
import com.crystal.todayprice.util.OnItemListItemListener
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

        if (item.items.isNotEmpty() && item.titleVisible) {
            binding.seeMoreTextView.setOnClickListener {
                onItemClickListener.onSeeMoreClick(item.items[0].viewType)
            }
        } else {
            binding.seeMoreTextView.visibility = View.GONE
        }
        if (item.items.isEmpty()) {
            binding.titleTextView.visibility = View.GONE
        }

    }
}