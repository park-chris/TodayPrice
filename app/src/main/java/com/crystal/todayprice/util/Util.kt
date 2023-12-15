package com.crystal.todayprice.util

import android.content.Intent
import android.os.Build
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.ui.market.MarketActivity
import java.io.Serializable

class Util {
    companion object {
        fun <T: Serializable> Intent.intentSerializable(key: String, type: Class<T>): T? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                this.getSerializableExtra(key, type)
            } else {
                this.getSerializableExtra(key) as T?
            }
        }
    }
}