package com.example.study.net

import com.example.service.network.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

//创建学习业务接口
interface StudyService {
    /**
     * 用户学习详情
     */
    @GET(value = "/member/study/info")
    fun getStudyInfo(): Call<BaseResponse>

    /**
     * 用户学习过的用户列表
     */
    @GET(value = "/member/courses/studied")
    fun getStudyList(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): Call<BaseResponse>

    /**
     * 用户购买的课程
     * 同样有page和size，默认 1，和 10
     */
    @GET("/member/courses/bought")
    fun getBoughtCourse(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): Call<BaseResponse>

    /**
     * 根据course_id 查询当前学员是否有课程，班级的权限
     */
    @GET("/course/authority")
    fun getCoursePermission(@Query("course_id") courseId: Int): Call<BaseResponse>

    /**
     * 根据course_id 查询课程章节
     */
    @GET("/course/chapter")
    fun getCourseChapter(@Query("course_id") courseId: Int): Call<BaseResponse>


    /**
     * 根据课程key，获取播放地址
     */
    @GET("/lesson/play/v2")
    fun getCoursePlayUrl(@Query("key") key: String): Call<BaseResponse>
}