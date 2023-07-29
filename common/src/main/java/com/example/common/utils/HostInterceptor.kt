package com.example.common.utils

import androidx.core.net.toUri
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 用于调试开发期间，替换baseHost的拦截器
 */
class HostInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val oldHost = originRequest.url.host
        val oldUrlStr = originRequest.url.toString()
        //调试使用
        val newHost = getBaseHost().toUri().host ?: oldHost
        val newUrlStr = if (newHost == oldHost) {
            oldUrlStr
        } else oldUrlStr.replace(oldHost, newHost)

        val newBuilder = originRequest.newBuilder()
        newBuilder.url(newUrlStr)
        return chain.proceed(newBuilder.build())
    }
}