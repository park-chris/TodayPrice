package com.crystal.todayprice.data

data class News(
    val newsId: Int = -1,
    val newsContent: String = "",
    val newsFilePath: String = "",
    val newsTitle: String = "",
    val newsDate: String = "",
): ListItem {
    override val viewType: ViewType
        get() = ViewType.NEWS
}
