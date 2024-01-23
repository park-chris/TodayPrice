package com.crystal.todayprice.component

import com.crystal.todayprice.data.ListItem

interface OnItemListItemListener {
    fun onItemClick(listItem: ListItem)
}