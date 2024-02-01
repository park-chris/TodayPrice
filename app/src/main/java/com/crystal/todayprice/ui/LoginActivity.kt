package com.crystal.todayprice.ui

import android.os.Bundle
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityLoginBinding

class LoginActivity: BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)


    }

    override fun onResume() {
        super.onResume()

        setupEvent()
    }

    private fun setupEvent() {
        binding.googleSignInButton.setOnClickListener {
            // TODO 구글 로그인
        }
        binding.kakaoSignInButton.setOnClickListener {
            // TODO 카카오 로그인
        }
    }

}