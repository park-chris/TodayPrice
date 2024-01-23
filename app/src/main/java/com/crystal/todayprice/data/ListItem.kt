package com.crystal.todayprice.data

import java.io.Serializable

interface ListItem: Serializable {
    val viewType: ViewType

    fun getKey() = hashCode()
}

enum class ViewType {
    MARKET,
    NEWS,
    EMPTY,
}