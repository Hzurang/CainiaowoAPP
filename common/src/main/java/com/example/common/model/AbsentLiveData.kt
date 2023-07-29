package com.example.common.model

import androidx.lifecycle.LiveData

/**
 * 创建一个空的liveData对象类
 */
class AbsentLiveData <T :Any?>private constructor(): LiveData<T>(){
    init {
        postValue(null)
    }
    companion object{
        fun <T:Any?> create():LiveData<T>{
            return AbsentLiveData<T>()
        }
    }
}