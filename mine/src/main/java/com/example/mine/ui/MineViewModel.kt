package com.example.mine.ui

import androidx.lifecycle.MutableLiveData
import com.example.common.base.BaseViewModel
import com.example.mine.repo.IMineResource
import com.example.service.repo.UserInfo

class MineViewModel(val reposity: IMineResource) : BaseViewModel() {

    val userInfo = reposity._userInfoRegister

    fun getUserInfo(token: String?) = serverAwait {
        reposity.getUserInfo(token)
    }
}