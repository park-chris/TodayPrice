package com.crystal.todayprice.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.crystal.todayprice.component.ListItemDiffUtil
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.util.OnItemListItemListener
import com.crystal.todayprice.viewholder.ListItemViewHolder
import com.crystal.todayprice.viewholder.ViewHolderGenerator

class ListItemAdapter(
    private val onItemListItemListener: OnItemListItemListener
): ListAdapter<ListItem, ListItemViewHolder<*>>(ListItemDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item?.viewType?.ordinal ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder<*> {
        return ViewHolderGenerator.get(parent, viewType, onItemListItemListener)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder<*>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }


}

