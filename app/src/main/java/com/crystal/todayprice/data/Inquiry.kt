package com.crystal.todayprice.data

import com.crystal.todayprice.util.TextUtil
import java.util.UUID

data class Inquiry(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val type: String = "",
    val content: String = "",
    val userId: String = "",
    val answer: String = "",
    val date: String = TextUtil.todayDateString(),
    val isChecked: Boolean = false,
)
