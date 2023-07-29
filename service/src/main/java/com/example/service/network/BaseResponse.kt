package com.example.service.network

import androidx.annotation.Keep

/**
 * 和业务相关，可能会随着业务的调整而调整
 */
@Keep
data class BaseResponse(
    val code: Int,//响应码
    val data: String?,//响应的数据内容
    val message: String?//响应数据的结果描述
) {
    companion object {
        const val SERVER_CODE_FAILED = 0//接口请求响应业务处理 失败
        const val SERVER_CODE_SUCCESS = 1//接口请求响应业务处理 成功
    }

}
/**
 * |1| 成功|
 * |0 |失败|
 * |101| appid为空或者app不存在|
 * |102|签名错误|
 * |103|签名失效（已经使用过一次）|
 * |104|请求已失效（时间戳过期）|
 * |105|缺少必传参数|
 * |106|参数格式有误或者未按规则提交|
 * |201|缺少token|
 * |202|token无效/错误|
 * |203|token已过期|
 * |401|没有权限调用|
 * |501|数据库连接出错|
 * |502|读写数据库异常|
 **/