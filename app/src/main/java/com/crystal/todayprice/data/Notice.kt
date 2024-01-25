package com.crystal.todayprice.data

data class Notice(
    val id: Int = -1,
    val title: String = "",
    val subtitle: String = "",
    val content: String = "",
    val imageUrl: String = "",
): ListItem {
    override val viewType: ViewType
        get() = ViewType.NOTICE
}

