package com.example.home.ui

import com.example.common.base.BaseViewModel
import com.example.home.repo.IHomeReposity

class HomeViewModel(val reposity: IHomeReposity) : BaseViewModel() {

    val liveBanner = reposity.liveBanner
    val liveHomeList = reposity.liveHomeList

    fun getHomeList() = serverAwait {
        reposity.getHomeList()
    }

    fun getBanner() = serverAwait {
        reposity.getBannerDatas()
    }

    suspend fun getModuleDatas(moduleId: Int) = reposity.getModuleDatas(moduleId)
}