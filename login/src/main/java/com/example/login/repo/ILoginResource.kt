package com.example.login.repo

import androidx.lifecycle.LiveData
import com.example.login.net.LoginRequest
import com.example.login.net.LoginRsp
import com.example.login.net.RegisterRsp

/**
 * 登录模块的相关的抽象数据接口
 */
interface ILoginResource {
    val registerRsp:LiveData<RegisterRsp>//是否注册
    val loginRsp:LiveData<LoginRsp>//登录结果
    /**
     * 校验手机号是否注册，合法
     */
    suspend fun checkRegister(mobi:String)
    /**
     * 手机号合法的基础上，调用登录，获取登录结果token
     */
    suspend fun requestLogin(body:LoginRequest)
}