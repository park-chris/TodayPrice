package com.crystal.todayprice.data

import java.util.UUID

data class Review(
  val id: String = UUID.randomUUID().toString(),
  val userId: String = "",
  val marketId: Int = 0,
  val userName: String = "",
  val marketName: String = "",
  val content: String = "",
  val date: String = "",
  var likeCount: Int = 0,
  val likeUsers: List<String> = emptyList(),
  val blockUsers: List<String> = emptyList(),
)