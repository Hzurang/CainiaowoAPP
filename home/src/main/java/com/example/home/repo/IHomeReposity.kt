package com.example.home.repo

import androidx.lifecycle.LiveData
import com.example.home.net.BannerList
import com.example.home.net.HomeList
import com.example.common.network.model.DataResult
import com.example.service.network.BaseResponse

interface IHomeReposity {

    val liveBanner: LiveData<BannerList>
    val liveHomeList: LiveData<HomeList>

    suspend fun getBannerDatas()

    suspend fun getHomeList()

    suspend fun  getModuleDatas(moduleId: Int): DataResult<BaseResponse>

}