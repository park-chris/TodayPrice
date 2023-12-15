package com.crystal.todayprice.data

import java.io.Serializable

data class Market(
    val id: String,
    val name: String,
    val imgUrl: String,
    val address: String,
) : Serializable
