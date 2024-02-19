package com.crystal.todayprice.data

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val provider: UserProvider = UserProvider.GOOGLE,
    val favoriteList: List<Int> = emptyList(),
)

enum class UserProvider {
    GOOGLE, KAKAO
}