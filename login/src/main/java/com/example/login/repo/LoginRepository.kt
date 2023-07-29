package com.example.login.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.common.model.SingleLiveData
import com.example.login.net.LoginRequest
import com.example.login.net.LoginRsp
import com.example.login.net.LoginService
import com.example.login.net.RegisterRsp
import com.example.common.network.support.serverData
import com.example.service.network.onBizzError
import com.example.service.network.onBizzOK
import com.example.service.network.onFailure
import com.example.service.network.onSuccess

class LoginRepository(private val service: LoginService) : ILoginResource {
    private val _registerRsp = SingleLiveData<RegisterRsp>()
    private val _loginRsp = SingleLiveData<LoginRsp>()

    override val registerRsp: LiveData<RegisterRsp>
        get() = _registerRsp
    override val loginRsp: LiveData<LoginRsp>
        get() = _loginRsp

    override suspend fun checkRegister(mobi: String) {
        service.isRegister(mobi)
            .serverData()
            .onSuccess {
                onBizzError { code, message ->
                    LogUtils.w("是否注册 BizError $code,$message")
                    ToastUtils.showShort(message)
                }
                //此处传入的实例类型即为解密后的json串生成的实例
                onBizzOK<RegisterRsp> { code, data, message ->
                    _registerRsp.value = data
                    LogUtils.i("是否注册 BizOK $data")
                    return@onBizzOK
                }
            }.onFailure {
                LogUtils.e("是否注册 接口异常 ${it.message}")
                ToastUtils.showShort(it.message)
            }
    }

    override suspend fun requestLogin(body: LoginRequest) {
        service.login(body)
            .serverData()
            .onSuccess {
                onBizzError { code, message ->
                    LogUtils.w("登录接口 BizError $code,$message")
                    ToastUtils.showShort(message)
                }
                //此处传入的实例类型即为解密后的json串生成的实例
                onBizzOK<LoginRsp> { code, data, message ->
                    _loginRsp.value = data
                    //同步到room数据库，登录状态

                    LogUtils.i("登录接口 BizOK $data")
                    return@onBizzOK
                }
            }.onFailure {
                LogUtils.e("登录接口 接口异常 ${it.message}")
                LogUtils.e("登录接口 异常 ${it.message}")
            }
    }
}