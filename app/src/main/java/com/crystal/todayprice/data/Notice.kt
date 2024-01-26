package com.crystal.todayprice.data

import java.io.Serializable

data class Notice(
    val id: Int = -1,
    val title: String = "",
    val subtitle: String = "",
    val content: String = "",
    val imageUrl: String = "",
): Serializable , ListItem {
    override val viewType: ViewType
        get() = ViewType.NOTICE
}

