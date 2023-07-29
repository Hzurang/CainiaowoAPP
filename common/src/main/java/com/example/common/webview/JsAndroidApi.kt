package com.example.common.webview

import android.webkit.JavascriptInterface
import com.blankj.utilcode.util.LogUtils
import com.example.common.network.config.SP_KEY_USER_TOKEN
import com.example.common.utils.CniaoSpUtils

object JsAndroidApi {

    const val JS_CALL_APP_KEY = "cniaoApp"

    @JavascriptInterface
    fun getAppToken(): String {
        LogUtils.w("JsAndroidApi 中 js调用了getToken")
        return CniaoSpUtils.getString(SP_KEY_USER_TOKEN) ?: ""
    }
}