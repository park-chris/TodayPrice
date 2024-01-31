package com.crystal.todayprice.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.crystal.todayprice.adapter.ReviewAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Review
import com.crystal.todayprice.databinding.ActivityReviewBinding
import com.crystal.todayprice.repository.MarketRepositoryImpl
import com.crystal.todayprice.repository.ReviewRepositoryImpl
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        marketId = intent.getIntExtra(MarketActivity.MARKET_ID, -1)

        setRecyclerView()

        binding.addEditText.setOnTouchListener { _, event ->

            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = binding.addEditText.compoundDrawablesRelative[2] // 오른쪽에 있는 drawable
                if (user != null && drawableEnd != null && event.rawX >= (binding.addEditText.right - drawableEnd.bounds.width())) {
                    // Drawable을 클릭한 경우 여기에 처리할 내용을 추가하세요.

                    return@setOnTouchListener true
                }
                if (user == null && drawableEnd != null && event.rawX >= (binding.addEditText.right - drawableEnd.bounds.width())) {
                    // Drawable을 클릭한 경우 여기에 처리할 내용을 추가하세요.

                    Toast.makeText(this, "로그인 후 이용해주세요.", Toast.LENGTH_SHORT).show()
                    return@setOnTouchListener true
                }
            }
            false
        }

    }

    private fun setRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(VerticalDividerItemDecoration(this,20, 20))

        reviewViewModel.reviews.observe(this, Observer {
            adapter.submitList(it)
        })

        reviewViewModel.getReviews(marketId)
    }


}