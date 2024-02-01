package com.crystal.todayprice

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class LaunchApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // KaKao SDK initialized
        KakaoSdk.init(this, BuildConfig.KAKAO_KEY )
    }
}