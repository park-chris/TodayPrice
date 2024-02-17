package com.crystal.todayprice.data

import com.crystal.todayprice.component.UserDataManager
import java.io.Serializable

data class Market(
    val id: Int = -1,
    val name: String = "",
    val imgUrl: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val type: Type = Type.NONE,
    val borough: String = "",
    val description: String = "",
    val phoneNumber: String = "",
    val reviewCount: Int = 0,
    var isFavorite: Boolean = UserDataManager.getInstance().user?.favoriteList?.contains(id) ?: false,
) : Serializable, ListItem {

    override val viewType: ViewType
        get() = ViewType.MARKET
}


/**
 * Building : 건물형
 * UndergroundMall : 지하도상가
 * Street : 골목형
 * ShopStreet : 상점가
 */
enum class Type {
    NONE, BUILDING, UNDERGROUND_MALL, STREET, SHOP_STREET, MIX
}
