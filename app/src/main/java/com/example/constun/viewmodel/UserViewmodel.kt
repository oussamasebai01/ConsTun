package com.example.constun.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.constun.model.User

class UserViewmodel: ViewModel() {

    private val userData = MutableLiveData<User>()
}