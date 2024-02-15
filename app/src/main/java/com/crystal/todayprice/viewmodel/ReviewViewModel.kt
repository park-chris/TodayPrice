package com.crystal.todayprice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.data.Review
import com.crystal.todayprice.repository.ReviewRepository
import com.crystal.todayprice.util.FirebaseCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviewViewModel(private val reviewRepository: ReviewRepository): ViewModel() {

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> by lazy { _reviews }

    fun getReviews(marketId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val reviews = reviewRepository.getReview(marketId)
            _reviews.postValue(reviews)
        }
    }

    fun getReviews(userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val reviews = reviewRepository.getUserReview(userId)
            _reviews.postValue(reviews)
        }
    }

    fun addReview(review: Review, callback: FirebaseCallback) {
        reviewRepository.addReview(review, callback)
    }

    fun updateReview(review: Review, userId: String) {
        reviewRepository.updateReview(review, userId)
    }

    fun updateBlockUser(reviewId: String, isContained: Boolean, userId: String) {
        reviewRepository.updateBlockUser(reviewId, isContained, userId)
    }

    fun deleteReview(reviewId: String, callback: FirebaseCallback) {
        reviewRepository.deleteReview(reviewId, callback)
    }

    class ReviewViewModelFactory(private val reviewRepository: ReviewRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReviewViewModel(reviewRepository) as T
        }
    }

}