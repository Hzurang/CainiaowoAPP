package com.example.home.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.example.home.net.BannerList
import com.example.home.net.HomeList
import com.example.home.net.HomeService
import com.example.common.network.model.DataResult
import com.example.common.network.support.serverData
import com.example.service.network.*

class HomeRepo(val service: HomeService) : IHomeReposity {
    private val _liveBanner = MutableLiveData<BannerList>()
    private val _liveHomeList = MutableLiveData<HomeList>()

    override val liveBanner: LiveData<BannerList>
        get() = _liveBanner
    override val liveHomeList: LiveData<HomeList>
        get() = _liveHomeList

    override suspend fun getBannerDatas() {
        service.getBanner()
            .serverData()
            .onSuccess {
                onBizzOK<BannerList> { code, data, message ->
                    _liveBanner.value = data
                    LogUtils.w("获取banner信息 onBizzOK $data")
                }
                onBizzError { code, message ->
                    _liveBanner.value = null
                    LogUtils.w("获取banner信息 BizError $code,$message")
                }
            }
            .onFailure {
                _liveBanner.value = null
                LogUtils.e("获取banner信息 接口异常 ${it.message}")
            }
    }

    override suspend fun getHomeList() {
        service.getHomeList()
            .serverData()
            .onSuccess {
                onBizzError { code, message ->
                    _liveHomeList.value = null
                    LogUtils.w("获取首页模块列表 BizError $code,$message")

                }
                onBizzOK<HomeList> { code, data, message ->
                    _liveHomeList.value = data
                    LogUtils.w("获取首页模块列表 onBizzOK $data")
                }
            }
            .onFailure {
                _liveHomeList.value = null
                LogUtils.e("获取首页模块列表 接口异常 ${it.message}")
            }


    }

    override suspend fun getModuleDatas(moduleId: Int) =
        service.getModuleDatas(moduleId).serverData()


}