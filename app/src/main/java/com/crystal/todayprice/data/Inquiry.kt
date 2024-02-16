package com.crystal.todayprice.data

import java.util.UUID

data class Inquiry(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val type: String = "",
    val content: String = "",
    val userId: String = "",
    val answer: String = "",
    val isChecked: Boolean = false,
)
