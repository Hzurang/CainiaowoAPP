package com.example.mine.net

import com.example.service.network.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MineService {
    /**
     * 获取用户信息
     * @param token String?
     * @return Call<BaseResponse>
     */
    @GET(value = "/member/userinfo")
    fun getUserInfo(@Header("token") token: String?): Call<BaseResponse>
}