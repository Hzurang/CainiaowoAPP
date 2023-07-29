package com.example.common.network.support

import androidx.lifecycle.LiveData
import com.example.common.network.model.ApiResponse
import com.example.common.network.model.UNKNOWN_ERROR_CODE
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 用于将retrofit的call回调数据转化为liveData的adapter
 */
class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(
                                ApiResponse.create(
                                    response
                                )
                            )
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(
                                ApiResponse.create(
                                    UNKNOWN_ERROR_CODE,
                                    throwable
                                )
                            )
                        }
                    })
                }
            }
        }
    }
}