package com.crystal.todayprice.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.crystal.todayprice.R
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityProfileBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
        userDataManager.user?.let {
            binding.item = userDataManager.user
        }
        setupEvent()
    }

    override fun onDestroy() {
        binding.navigationView.setNavigationItemSelectedListener(null)
        super.onDestroy()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_my_favorite -> startActivity(Intent(this, MyFavoriteActivity::class.java))
            R.id.action_my_inquiry_list -> startActivity(
                Intent(
                    this,
                    MyInquiryActivity::class.java
                )
            )
            R.id.action_logout -> {
                userDataManager.user ?: return false
                FirebaseAuth.getInstance().signOut()
                userDataManager.user = null
                updateProfile(null)
                actionMenuHome()
            }
            R.id.action_my_review -> startActivity(Intent(this, UserReviewActivity::class.java))
            R.id.action_delete_account -> {}
        }
        return false
    }


    private fun setupEvent() {
        binding.editTextView.setOnClickListener {
            startActivity(Intent(this, EditNameActivity::class.java))
        }
    }

}