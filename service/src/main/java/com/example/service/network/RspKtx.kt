package com.example.service.network

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.example.common.network.model.DataResult
import com.example.common.network.model.succeeded
import com.example.common.network.support.CniaoUtils
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)//抑制contract警告
inline fun <R> DataResult<R>.onSuccess(action: R.() -> Unit): DataResult<R> {
    //契约类，助编译器智能推导用的
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (succeeded) action.invoke((this as DataResult.Success<R>).data)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <R> DataResult<R>.onFailure(action: (Throwable) -> Unit): DataResult<R> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is DataResult.Error) action.invoke(exception)
    return this
}

/**
 * 接口成功，但是业务返回code不是1的情况
 * @receiver BaseResponse
 * @param block Function2<[@kotlin.ParameterName] Int, [@kotlin.ParameterName] String?, Unit>
 * @return BaseResponse
 */
@OptIn(ExperimentalContracts::class)
inline fun BaseResponse.onBizzError(block: (code: Int, message: String?) -> Unit): BaseResponse {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    if (code != BaseResponse.SERVER_CODE_SUCCESS) {
        block.invoke(code, message ?: "Error Message Null")
    }
    return this
}

/**
 * 接口成功且业务成功code == 1的情况
 * @receiver BaseResponse
 * @param block Function3<[@kotlin.ParameterName] Int, [@kotlin.ParameterName] T?, [@kotlin.ParameterName] String?, Unit>
 * @return BaseResponse
 */
@OptIn(ExperimentalContracts::class)
inline fun <reified T> BaseResponse.onBizzOK(crossinline block: (code: Int, data: T?, message: String?) -> Unit): BaseResponse {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    if (code == BaseResponse.SERVER_CODE_SUCCESS) {
        block.invoke(code, toEntry<T>(), message)
    }
    return this
}

/**
 * 这里表示网络请求成功，并得到业务服务器的响应，至于业务成功失败，另一说
 * 将BaseCniaoRsp的对象，转化为需要的对象类型，也就是将body.string转为entity
 * @receiver BaseResponse
 * @return T? 返回需要的类型对象，可能为null，如果json解析失败的话
 */
inline fun <reified T> BaseResponse.toEntry(): T? {
    if (data == null) {
        LogUtils.e("Server Response Json Ok, But data=null, $code,$message")
        return null
    }
    val decodeData = CniaoUtils.decodeData(data)
    //gson不允许我们将json对象采用String,所以单独处理
    if (T::class.java.isAssignableFrom(String::class.java)) {
        return decodeData as T
    }
    return kotlin.runCatching {
        GsonUtils.fromJson(decodeData, T::class.java)
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()
}
