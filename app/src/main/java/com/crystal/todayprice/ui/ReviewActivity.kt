package com.crystal.todayprice.ui

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.ReviewAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ButtonType
import com.crystal.todayprice.component.CustomDialog
import com.crystal.todayprice.component.CustomModalBottomSheet
import com.crystal.todayprice.component.ModalType
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Review
import com.crystal.todayprice.databinding.ActivityReviewBinding
import com.crystal.todayprice.repository.Result
import com.crystal.todayprice.repository.ReviewRepositoryImpl
import com.crystal.todayprice.util.FirebaseCallback
import com.crystal.todayprice.util.OnBottomSheetListener
import com.crystal.todayprice.util.OnDialogListener
import com.crystal.todayprice.util.OnItemReviewListener
import com.crystal.todayprice.util.VerticalDividerItemDecoration
import com.crystal.todayprice.viewmodel.ReviewViewModel
import com.kakao.sdk.common.KakaoSdk.type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

private const val TAG = "TestLog"

class ReviewActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityReviewBinding
    private val reviewViewModel: ReviewViewModel by viewModels {
        ReviewViewModel.ReviewViewModelFactory(ReviewRepositoryImpl())
    }
    private val user = userDataManager.user

    private lateinit var adapter: ReviewAdapter
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

                val text = binding.addEditText.text.toString()
                val today = System.currentTimeMillis()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN)
                val date = dateFormat.format(today)
                val review = Review(
                    userId = user.id,
                    marketId = marketId,
                    userName = user.name,
                    content = text,
                    date = date
                )

                addReview(review)

            } else {
                val dialog = CustomDialog(this, object : OnDialogListener {
                    override fun onOk() {
                        startActivity(Intent(this@ReviewActivity, LoginActivity::class.java))
                    }
                })
                dialog.start(
                    title = null,
                    message = getString(R.string.dialog_login_message),
                    leftButtonText = getString(R.string.cancel),
                    rightButtonText = getString(R.string.go_login),
                    isCanceled = true
                )
            }
        }
    }

    private fun addReview(review: Review) {
        reviewViewModel.addReview(review, object : FirebaseCallback {
            override fun onResult(result: Result) {
                when (result) {
                    Result.SUCCESS -> {
                        val list = mutableListOf<Review>()
                        list.addAll(adapter.getList())
                        list.add(0, review)
                        adapter.submitList(list)

                        clearEditText()
                    }

                    Result.FAIL -> {
                    }
                }
            }
        })
    }

    private fun updateReviewLike(review: Review, upCount: Int, userId: String) {
        val index = adapter.getList().indexOf(review)
        val newLikeUsers = mutableListOf<String>()

        newLikeUsers.addAll(review.likeUsers)

        if (upCount == -1) {
            newLikeUsers.remove(userId)
        } else {
            newLikeUsers.add(userId)
        }

        val likeCount = review.likeCount + upCount
        val newReview = review.copy(
            likeUsers = newLikeUsers,
            likeCount = likeCount
        )
        adapter.updateReview(index, newReview)
        reviewViewModel.updateReview(newReview, userId)
    }

    private fun updateReviewBlock(review: Review, isContained: Boolean, userId: String) {
        val index = adapter.getList().indexOf(review)
        val newBlockUsers = mutableListOf<String>()

        newBlockUsers.addAll(review.blockUsers)

        if (isContained) {
            newBlockUsers.remove(userId)
        } else {
            newBlockUsers.add(userId)
        }
        val newReview = review.copy(
            blockUsers = newBlockUsers,
        )
        adapter.updateReview(index, newReview)
        reviewViewModel.updateBlockUser(review.id, isContained, userId)
    }

    private fun clearEditText() {
        binding.addEditText.text = null
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.addEditText.windowToken, 0)
        binding.addEditText.clearFocus()

        CoroutineScope(Dispatchers.IO).launch {
            delay(100)
            withContext(Dispatchers.Main) {
                binding.recyclerView.scrollToPosition(0)
            }
        }
    }

    private fun startDialog(review: Review, modalType: ModalType, userId: String) {
        val dialog = CustomModalBottomSheet(this@ReviewActivity, object : OnBottomSheetListener {
            override fun onClick(buttonType: ButtonType) {
                when (buttonType) {
                    ButtonType.REPORT -> {
                        // TODO 신고창 열기
                        Toast.makeText(this@ReviewActivity, "신고버튼", Toast.LENGTH_SHORT).show()
                    }
                    ButtonType.BLOCK -> {
                        val isContained = review.blockUsers.contains(userId)
                        updateReviewBlock(review, isContained, userId)
                    }
                    ButtonType.DELETE -> {
                        // TODO 삭제 기능
                        Toast.makeText(this@ReviewActivity, "삭제기능", Toast.LENGTH_SHORT).show()
                    }
                    ButtonType.OPEN_BLOCK -> {
                        startDialog(review, ModalType.BLOCK, userId)
                    }
                    ButtonType.OPEN_UNBLOCK -> {
                        startDialog(review, ModalType.UNBLOCK, userId)
                    }
                }
            }

        })
        dialog.show(review.userName, modalType)
    }

    private fun setRecyclerView() {

        adapter = ReviewAdapter(object : OnItemReviewListener {

            override fun onLikeClick(review: Review) {
                if (user == null) {
                    Toast.makeText(
                        this@ReviewActivity,
                        getString(R.string.require_login),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                val isContained = review.likeUsers.contains(user.id)
                if (isContained) {
                    updateReviewLike(review, -1, user.id)
                } else {
                    updateReviewLike(review, 1, user.id)
                }
            }

            override fun onMenuClick(review: Review) {
                if (user == null) {
                    Toast.makeText(
                        this@ReviewActivity,
                        getString(R.string.require_login),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                if (review.userId != user.id && review.blockUsers.contains(user.id)) {
                    startDialog(review, ModalType.MENU_ANOTHER_UNBLOCK, user.id)
                } else if (review.userId == user.id) {
                    startDialog(review, ModalType.DELETE, user.id)
                } else {
                    startDialog(review, ModalType.MENU_ANOTHER_BLOCK, user.id)
                }

            }

        })


        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(VerticalDividerItemDecoration(this, 20, 20))

        reviewViewModel.reviews.observe(this, Observer {
            adapter.submitList(it)

            if (it.isNotEmpty()) {
                binding.infoTextView.isVisible = false
            }
        })

        reviewViewModel.getReviews(marketId)
    }


}