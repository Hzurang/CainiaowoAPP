package com.example.common.network.model

/**
 * 数据响应的封装类 ，密封类sealed
 * 其实可以使用kotlin标准库中的Result
 */
sealed class DataResult<out R> {
    //成功状态
    data class Success<out T>(val data: T) : DataResult<T>()

    //错误、失败状态
    data class Error(val exception: Exception) : DataResult<Nothing>()

    //加载数据中
    object Loading : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }

}

/**
 *  扩展属性返回结果如果是Success类，且data非null，才认为是成功的。
 */
val DataResult<*>.succeeded
    get() = this is DataResult.Success && data != null


//Resource形式 数据封装

//资源获取的状态，枚举
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

/**
 * 数据封装
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                "Resource Success"
            )
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }
}