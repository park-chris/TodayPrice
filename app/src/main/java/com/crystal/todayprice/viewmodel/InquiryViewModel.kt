package com.crystal.todayprice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.data.Inquiry
import com.crystal.todayprice.data.Notice
import com.crystal.todayprice.repository.InquiryRepository
import com.crystal.todayprice.repository.NoticeRepository
import com.crystal.todayprice.util.FirebaseCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InquiryViewModel(private val inquiryRepository: InquiryRepository): ViewModel() {

    private val _inquiries= MutableLiveData<List<Inquiry>>()
    val inquiries: LiveData<List<Inquiry>> by lazy { _inquiries }

    fun getMyInquiries(userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val myInquiries = inquiryRepository.getMyInquiry(userId)
            _inquiries.postValue(myInquiries)
        }
    }

    fun addInquiry(inquiry: Inquiry, callback: FirebaseCallback) {
        inquiryRepository.addInquiry(inquiry, callback)
    }

    fun deleteInquiry(inquiry: Inquiry, callback: FirebaseCallback) {
        inquiryRepository.deleteInquiry(inquiry, callback)
    }

    class InquiryViewModelFactory(private val inquiryRepository: InquiryRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return InquiryViewModel(inquiryRepository) as T
        }
    }

}