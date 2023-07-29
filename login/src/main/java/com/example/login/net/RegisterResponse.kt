package com.example.login.net

import androidx.annotation.Keep
import com.example.service.repo.UserInfo

/**
 * 查询手机号码是否注册的接口响应
 */
@Keep
data class RegisterRsp(
    val is_register: Int = FLAG_UN_REGISTERED// 1 表示注册，0 表示未注册
) {
    companion object {
        const val FLAG_IS_REGISTERED = 1//已经注册的
        const val FLAG_UN_REGISTERED = 0//0 表示未注册
    }
}

/**
 * 手机号和密码登录 接口响应
 */
typealias LoginRsp = UserInfo