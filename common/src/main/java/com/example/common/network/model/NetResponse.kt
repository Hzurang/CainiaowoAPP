package com.example.common.network.model

/**
 * 基础的网络放回数据结构
 */
data class NetResponse(
    val code: Int,//响应码
    val data: Any?,//响应数据内容
    val message: String//响应数据的结果描述
)