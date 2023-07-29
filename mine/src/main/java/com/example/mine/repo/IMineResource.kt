package com.example.mine.repo

import androidx.lifecycle.LiveData
import com.example.mine.net.UserInfoRsp

interface IMineResource {
    /**
     * 用户信息返回的数据类
     */
    val _userInfoRegister: LiveData<UserInfoRsp?>

    /**
     * 获取用户信息
     * @param token String?
     */
    suspend fun getUserInfo(token: String?)
}