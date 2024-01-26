package com.crystal.todayprice.util

import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.data.ViewType

interface OnItemListItemListener {
    fun onItemClick(listItem: ListItem)
    fun onSeeMoreClick(viewType: ViewType)
}