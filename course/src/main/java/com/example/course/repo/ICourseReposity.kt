package com.example.course.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.course.net.CourseList
import com.example.course.net.CourseTypes
import kotlinx.coroutines.flow.Flow

interface ICourseReposity {
    val liveCourseType:LiveData<CourseTypes?>

    suspend fun getCourseCategory()

    suspend fun getCourseList(
        course_type: Int,//类型 (-1 全部) (1 普通课程) (2 职业课程/班级课程) (3 实战课程) 默认 -1
        code: String,//方向从课程分类接口获取    默认 all;例如 android,python
        difficulty: Int,//难度 (-1 全部) (1 初级) (2 中级) (3 高级) (4 架构) 默认 -1
        is_free: Int,//价格 (-1, 全部) （0 付费） (1 免费) 默认 -1
        q: Int,//排序  (-1 最新) (1 评价最高)  (2 学习最多) 默认 -1
    ):Flow<PagingData<CourseList.Data>>

}