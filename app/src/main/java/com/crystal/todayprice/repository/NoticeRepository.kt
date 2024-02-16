package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Notice

interface NoticeRepository {
    suspend fun getNotice(): List<Notice>
}