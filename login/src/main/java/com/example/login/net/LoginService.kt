package com.example.login.net

import com.example.service.network.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @GET("accounts/phone/is/register")
    fun isRegister(@Query(value = "mobi") mobile: String): Call<BaseResponse>

    @POST("accounts/course/10301/login")
    fun login(@Body requestBody: LoginRequest): Call<BaseResponse>
}