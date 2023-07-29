package com.example.common.utils

import com.example.common.BuildConfig
import com.example.common.utils.Config.Companion.BASE_URL
import com.example.common.utils.Config.Companion.HOST

/**
 * 基础baseUrl的配置，可用于dokit的serverHost
 */
class Config {
    companion object {
        //配置host的key
        const val HOST = "host"

        //正式域名
        const val BASE_URL = "https://course.api.cniao5.com/"

        //测试域名
        const val TEST_BASE_URL = "https://course.api.cniao5.com.test/"
    }
}

/**
 * 获取当前配置的baseHost
 */
fun getBaseHost(): String {
    //从sp中获取
    return if (BuildConfig.DEBUG) {
        CniaoSpUtils.getString(HOST) ?: BASE_URL
    } else {
        BASE_URL
    }

}

/**
 * 更新配置host
 */
fun setBaseHost(host: String) {
    //设置到sp中
    CniaoSpUtils.put(Config.HOST, host)
}