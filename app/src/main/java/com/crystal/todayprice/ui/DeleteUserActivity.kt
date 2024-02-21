package com.crystal.todayprice.ui


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityDeleteUserBinding
import com.crystal.todayprice.repository.InquiryRepositoryImpl
import com.crystal.todayprice.repository.ReviewRepositoryImpl
import com.crystal.todayprice.viewmodel.InquiryViewModel
import com.crystal.todayprice.viewmodel.ReviewViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DeleteUserActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityDeleteUserBinding

    private val inquiryViewModel: InquiryViewModel by viewModels {
        InquiryViewModel.InquiryViewModelFactory(InquiryRepositoryImpl())
    }
    private val reviewViewModel: ReviewViewModel by viewModels {
        ReviewViewModel.ReviewViewModelFactory(ReviewRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeleteUserBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        setupEvent()
    }

    private fun setupEvent() {
        binding.deleteButton.setOnClickListener {
            baseBinding.progressBar.visibility = View.VISIBLE

            userDataManager.user?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    inquiryViewModel.deleteAccountInquiry(it.id)
                    userViewModel.deleteAccount(it)
                    reviewViewModel.deleteAccount(it.id)
                    FirebaseAuth.getInstance().currentUser?.delete()?.await()
                    baseBinding.progressBar.visibility = View.GONE
                }
                actionMenuHome()
            }
        }
    }

}