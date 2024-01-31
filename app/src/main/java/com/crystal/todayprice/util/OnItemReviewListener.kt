package com.crystal.todayprice.util

import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.data.Review
import com.crystal.todayprice.data.ViewType

interface OnItemReviewListener {
    fun onLikeClick(review: Review)
    fun onMenuClick(review: Review)
}