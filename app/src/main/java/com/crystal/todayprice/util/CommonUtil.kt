package com.crystal.todayprice.util

import android.content.Intent
import android.os.Build
import java.io.Serializable

class CommonUtil {
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