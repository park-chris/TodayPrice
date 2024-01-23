package com.crystal.todayprice.component

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.crystal.todayprice.data.ListItem

class ListItemDiffUtil<T: ListItem>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.viewType == newItem.viewType && oldItem.getKey() == newItem.getKey()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
       return oldItem == newItem
    }

}