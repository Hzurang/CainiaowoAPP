package com.example.home.net

import com.example.service.network.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    /**
     * 获取页面有几个模块
     * 根据页面id 获取页面的模块列表
     * 首页用，id就是1
     */
    @GET("/allocation/module/list")
    fun getHomeList(@Query("page_id") pageId: Int = 1): Call<BaseResponse>

    /**
     *获取某个模块的列表
     */
    @GET("/allocation/component/list")
    fun getModuleDatas(@Query("module_id") moduleId: Int): Call<BaseResponse>

    @GET("/ad/new/banner/list")
    fun getBanner(
        @Query("type") type: Int = 2,//类型 1:小程序 2:web 3:h5 4:ios 5:android 如: 2表示web 默认2
        @Query("page_show") pageShow: Int = 1//页面显示 1 首页 2 课程 3 大数据学院 4 机器人学院 5 人工智能学院 6 推广员 默认1
    ): Call<BaseResponse>
}