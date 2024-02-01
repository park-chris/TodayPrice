package com.crystal.todayprice.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.lifecycle.Observer
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.ReviewAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.CustomDialog
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Review
import com.crystal.todayprice.databinding.ActivityReviewBinding
import com.crystal.todayprice.repository.MarketRepositoryImpl
import com.crystal.todayprice.repository.ReviewRepositoryImpl
import com.crystal.todayprice.util.OnDialogListener
import com.crystal.todayprice.util.OnItemReviewListener
import com.crystal.todayprice.util.VerticalDividerItemDecoration
import com.crystal.todayprice.viewmodel.MarketViewModel
import com.crystal.todayprice.viewmodel.ReviewViewModel
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "TestLog"

class ReviewActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityReviewBinding
    private val reviewViewModel: ReviewViewModel by viewModels {
        ReviewViewModel.ReviewViewModelFactory(ReviewRepositoryImpl())
    }
    private val user = FirebaseAuth.getInstance().currentUser


    private val adapter = ReviewAdapter(object : OnItemReviewListener {

        override fun onLikeClick(review: Review) {
            Log.e(TAG, "onLikeClick")
        }

        override fun onMenuClick(review: Review) {
            Log.e(TAG, "onMenuClick")
        }

    })

    private var marketId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        marketId = intent.getIntExtra(MarketActivity.MARKET_ID, -1)

        setRecyclerView()
        setupEvent()

    }

    override fun onResume() {
        super.onResume()

        setTextToEdit()

    }

    private fun setTextToEdit() {
        val text = if (user != null) {
            getString(R.string.edit_hint_user)
        } else {
            getString(R.string.edit_hint_anonymous)
        }

        binding.addEditText.hint = text
    }

    private fun setupEvent() {
        binding.addButton.setOnClickListener {
            if (user != null) {
                // TODO 댓글 작성 후 전송  기능
                val text = binding.addEditText.text.toString()
                Toast.makeText(this, "text: ${text}", Toast.LENGTH_SHORT).show()
            } else {
                val dialog = CustomDialog(this, object : OnDialogListener {
                    override fun onOk() {
                        startActivity(Intent(this@ReviewActivity, LoginActivity::class.java))
                    }
                })
                dialog.start(title = null, message = getString(R.string.dialog_login_message), leftButtonText = getString(R.string.cancel), rightButtonText = getString(R.string.go_login), isCanceled = true)

            }

        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(VerticalDividerItemDecoration(this,20, 20))

        reviewViewModel.reviews.observe(this, Observer {
            adapter.submitList(it)

            if (it.isNotEmpty()) {
                binding.infoTextView.isVisible = false
            }
        })

        reviewViewModel.getReviews(marketId)
    }


}