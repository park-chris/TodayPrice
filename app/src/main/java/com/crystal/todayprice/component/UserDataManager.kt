package com.crystal.todayprice.component

import com.crystal.todayprice.data.User

class UserDataManager private constructor(){
    var user: User? = null

    companion object {
        @Volatile
        private var instance: UserDataManager? = null
        fun getInstance(): UserDataManager {
            return instance ?: synchronized(this) {
                instance ?: UserDataManager().also { instance = it }
            }
        }
    }

}