package com.crystal.todayprice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.data.Notice
import com.crystal.todayprice.repository.NoticeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticeViewModel(private val noticeRepository: NoticeRepository): ViewModel() {

    private val _notices = MutableLiveData<List<Notice>>()
    val notices: LiveData<List<Notice>> by lazy { _notices }

    fun getNotice() {
        CoroutineScope(Dispatchers.IO).launch {
            val markets = noticeRepository.getNotice()
            _notices.postValue(markets)
        }
    }

    class NoticeViewModelFactory(private val noticeRepository: NoticeRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NoticeViewModel(noticeRepository) as T
        }
    }

}