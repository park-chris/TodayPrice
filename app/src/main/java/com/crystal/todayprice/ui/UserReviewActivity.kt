package com.crystal.todayprice.ui


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.UserReviewAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ButtonType
import com.crystal.todayprice.component.CustomModalBottomSheet
import com.crystal.todayprice.component.ModalType
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Review
import com.crystal.todayprice.data.User
import com.crystal.todayprice.databinding.ActivityUserReviewBinding
import com.crystal.todayprice.repository.ReviewRepositoryImpl
import com.crystal.todayprice.util.FirebaseCallback
import com.crystal.todayprice.util.OnBottomSheetListener
import com.crystal.todayprice.util.OnItemReviewListener
import com.crystal.todayprice.util.Result
import com.crystal.todayprice.util.VerticalDividerItemDecoration
import com.crystal.todayprice.viewmodel.ReviewViewModel

class UserReviewActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityUserReviewBinding
    private val reviewViewModel: ReviewViewModel by viewModels {
        ReviewViewModel.ReviewViewModelFactory(ReviewRepositoryImpl())
    }
    private  var user: User? = null

    private lateinit var adapter: UserReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserReviewBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
        baseBinding.progressBar.visibility = View.VISIBLE

        setRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        observeData()
    }

    private fun observeData() {
        user = userDataManager.user
        reviewViewModel.reviews.observe(this, Observer {
            adapter.submitList(it)
            baseBinding.progressBar.visibility = View.GONE

            if (it.isNotEmpty()) {
                binding.infoTextView.isVisible = false
            }
        })
        user ?: return
        reviewViewModel.getReviews(user!!.id)
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

    private fun deleteReview(review: Review, userId: String) {
        baseBinding.progressBar.visibility = View.VISIBLE
        if (review.userId == userId) {
            reviewViewModel.deleteReview(review.id, object : FirebaseCallback {
                override fun onResult(result: Result) {
                    baseBinding.progressBar.visibility = View.GONE
                    if (result == Result.SUCCESS) {
                        val index = adapter.getList().indexOf(review)
                        adapter.deleteReview(index)
                        Toast.makeText(this@UserReviewActivity, getString(R.string.review_delete_complete), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@UserReviewActivity, getString(R.string.retry), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun startDialog(review: Review, modalType: ModalType, userId: String) {
        val dialog = CustomModalBottomSheet(this@UserReviewActivity, object : OnBottomSheetListener {
            override fun onClick(buttonType: ButtonType) {
                when (buttonType) {
                    ButtonType.REPORT -> {
                        val intent = Intent(this@UserReviewActivity, ReportActivity::class.java)
                        intent.putExtra(ReportActivity.REVIEW_ID, review.id)
                        startActivity(intent)
                    }
                    ButtonType.BLOCK -> {
                        val isContained = review.blockUsers.contains(userId)
                        updateReviewBlock(review, isContained, userId)
                    }
                    ButtonType.DELETE -> {
                        deleteReview(review, userId)
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

        adapter = UserReviewAdapter(object : OnItemReviewListener {

            override fun onLikeClick(review: Review) {
                if (user == null) {
                    Toast.makeText(
                        this@UserReviewActivity,
                        getString(R.string.require_login),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                val isContained = review.likeUsers.contains(user!!.id)
                if (isContained) {
                    updateReviewLike(review, -1, user!!.id)
                } else {
                    updateReviewLike(review, 1, user!!.id)
                }
            }

            override fun onMenuClick(review: Review) {
                if (user == null) {
                    Toast.makeText(
                        this@UserReviewActivity,
                        getString(R.string.require_login),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (review.userId != user!!.id && review.blockUsers.contains(user!!.id)) {
                    startDialog(review, ModalType.MENU_ANOTHER_UNBLOCK, user!!.id)
                } else if (review.userId == user!!.id) {
                    startDialog(review, ModalType.DELETE, user!!.id)
                } else {
                    startDialog(review, ModalType.MENU_ANOTHER_BLOCK, user!!.id)
                }
            }
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(VerticalDividerItemDecoration(this, 20, 20))

    }


}