package com.example.course.ui

import android.widget.Adapter
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.base.BaseViewModel
import com.example.course.repo.ICourseReposity

class CourseViewModel(val repo: ICourseReposity) : BaseViewModel() {
    val liveCourseType = repo.liveCourseType
    val courseAdapter = CoursePageAdapter()

    fun getCourseCategory() = serverAwait {
        repo.getCourseCategory()
    }

    suspend fun getCourseList(
        course_type: Int = -1,//类型 (-1 全部) (1 普通课程) (2 职业课程/班级课程) (3 实战课程) 默认 -1
        code: String = "all",//方向从课程分类接口获取    默认 all;例如 android,python
        difficulty: Int = -1,//难度 (-1 全部) (1 初级) (2 中级) (3 高级) (4 架构) 默认 -1
        is_free: Int = -1,//价格 (-1, 全部) （0 付费） (1 免费) 默认 -1
        q: Int = -1,//排序  (-1 最新) (1 评价最高)  (2 学习最多) 默认 -1
    ) = repo.getCourseList(
        course_type,
        code,
        difficulty,
        is_free,
        q
    ).cachedIn(viewModelScope)//跟viewmodel的生命周期绑定
}