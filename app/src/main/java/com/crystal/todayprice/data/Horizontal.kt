package com.crystal.todayprice.data

data class Horizontal(
    val title: String,
    val items: List<ListItem>,
    val titleVisible: Boolean,
): ListItem {
    override val viewType: ViewType
        get() = ViewType.HORIZONTAL
}
