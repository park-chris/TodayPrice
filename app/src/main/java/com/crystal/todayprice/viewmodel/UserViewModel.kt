package com.crystal.todayprice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.data.User
import com.crystal.todayprice.repository.UserRepository
import com.crystal.todayprice.repository.UserRepositoryImpl
import com.crystal.todayprice.util.FirebaseCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(): ViewModel() {

    private val userRepository: UserRepository = UserRepositoryImpl()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> by lazy { _user }

    fun createUser(user: User, callback: FirebaseCallback) {
        userRepository.createUser(user, callback)
    }

    fun getUser(userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userRepository.getUser(userId)
            user?.let {
                _user.postValue(it)
            }
        }
    }

    fun setUser(user: User) {
        _user.postValue(user)
    }

}