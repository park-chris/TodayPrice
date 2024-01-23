package com.crystal.todayprice.viewholder

import androidx.recyclerview.widget.ListAdapter
import com.crystal.todayprice.adapter.HorizontalListAdapter
import com.crystal.todayprice.data.Horizontal
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.databinding.HorizontalItemBinding

class HorizontalViewHolder(
    private val binding: HorizontalItemBinding
): ListItemViewHolder<HorizontalItemBinding>(binding) {
    private val adapter = HorizontalListAdapter()

    init {
        binding.listView.adapter = adapter
    }

    override fun bind(item: ListItem) {
        super.bind(item)
        item as Horizontal
        binding.titleTextView.text = item.title
        adapter.submitList(item.items)
    }

}