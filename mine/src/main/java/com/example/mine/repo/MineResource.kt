package com.example.mine.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.mine.net.MineService
import com.example.mine.net.UserInfoRsp
import com.example.common.network.support.serverData
import com.example.service.network.onBizzError
import com.example.service.network.onBizzOK
import com.example.service.network.onFailure
import com.example.service.network.onSuccess

class MineResource(val service: MineService) : IMineResource {

    private val userInfoRegister = MutableLiveData<UserInfoRsp>()


    override val _userInfoRegister: LiveData<UserInfoRsp?>
        get() = userInfoRegister

    override suspend fun getUserInfo(token: String?) {
        service.getUserInfo(token)
            .serverData()
            .onSuccess {
                onBizzOK<UserInfoRsp> { code, data, message ->
                    userInfoRegister.value = data
                    LogUtils.w("获取用户信息 onBizzOK $data")
                }
                onBizzError { code, message ->
                    userInfoRegister.value = null
                    LogUtils.w("获取用户信息 BizError $code,$message")
                }
            }
            .onFailure {
                userInfoRegister.value = null
                LogUtils.e("获取用户信息 接口异常 ${it.message}")
            }


    }
}