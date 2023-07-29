package com.example.course.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.blankj.utilcode.util.LogUtils
import com.example.course.net.CourseList
import com.example.course.net.CourseService
import com.example.course.net.CourseTypes
import com.example.common.network.support.serverData
import com.example.service.network.onBizzError
import com.example.service.network.onBizzOK
import com.example.service.network.onFailure
import com.example.service.network.onSuccess
import kotlinx.coroutines.flow.Flow


class CourseReposity(val service: CourseService) : ICourseReposity {
    private val _liveCourseType = MutableLiveData<CourseTypes?>()

    override val liveCourseType: LiveData<CourseTypes?>
        get() = _liveCourseType

    override suspend fun getCourseCategory() {
        service.getCourseCategory()
            .serverData()
            .onSuccess {
                onBizzOK<CourseTypes> { code, data, message ->

                    LogUtils.i("获取课程分类 BizOK $data")
                    _liveCourseType.value = data

                }
                onBizzError { code, message ->
                    _liveCourseType.value = null
                    LogUtils.w("获取课程分类 BizError $code,$message")
                }
            }
            .onFailure {
                LogUtils.e("获取课程分类 接口异常 ${it.message}")
            }

    }

    private val pageSize = 10
    override suspend fun getCourseList(
        course_type: Int,
        code: String,
        difficulty: Int,
        is_free: Int,
        q: Int
    ): Flow<PagingData<CourseList.Data>> {
        val pagingConfig = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 5,
            initialLoadSize = 10,
            maxSize = pageSize * 3
        )
        return Pager(config = pagingConfig, initialKey = null) {
            CourseListPagingSource(
                service,
                course_type,
                code,
                difficulty,
                is_free,
                q
            )
        }.flow

    }

    class CourseListPagingSource constructor(
        private val service: CourseService,
        private val course_type: Int,//类型 (-1 全部) (1 普通课程) (2 职业课程/班级课程) (3 实战课程) 默认 -1
        private val code: String,//方向从课程分类接口获取    默认 all;例如 android,python
        private val difficulty: Int,//难度 (-1 全部) (1 初级) (2 中级) (3 高级) (4 架构) 默认 -1
        private val is_free: Int,//价格 (-1, 全部) （0 付费） (1 免费) 默认 -1
        private val q: Int,//排序  (-1 最新) (1 评价最高)  (2 学习最多) 默认 -1
    ) : PagingSource<Int, CourseList.Data>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CourseList.Data> {
            var result: LoadResult<Int, CourseList.Data> =
                LoadResult.Error<Int, CourseList.Data>(Exception("加载中"))
            val pageNum = params.key ?: 1
            service.getCourseList(
                course_type,
                code,
                difficulty,
                is_free,
                q,
                page = pageNum
            )
                .serverData()
                .onSuccess {
                    onBizzError { code, message ->
                        LogUtils.w("获取type类型的课程列表 BizError $code,$message")
                        result = LoadResult.Error<Int, CourseList.Data>(Exception(message))
                    }
                    onBizzOK<CourseList> { code, data, message ->
                        LogUtils.i("获取type类型的课程列表 BizOK $data")
                        val totalPageNum = data?.total_page ?: 0
                        result = LoadResult.Page<Int, CourseList.Data>(
                            data = data?.datas ?: emptyList(),
                            prevKey = if (pageNum == 1) null else pageNum-1,
                            nextKey = if (pageNum < totalPageNum) pageNum+1 else null
                        )
                    }
                }
                .onFailure {
                    LogUtils.e("获取type类型的课程列表 接口异常 ${it.message}")
                    result = LoadResult.Error<Int, CourseList.Data>(it)
                }
            return result
        }
    }
}