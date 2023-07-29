package com.example.study.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.blankj.utilcode.util.LogUtils
import com.example.common.model.SingleLiveData
import com.example.study.net.*
import com.example.common.network.support.serverData
import com.example.common.utils.getBaseHost
import com.example.service.network.onBizzError
import com.example.service.network.onBizzOK
import com.example.service.network.onFailure
import com.example.service.network.onSuccess
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class StudyResource(private val service: StudyService) : IStudyResource {
    private val pageSize = 100

    private val _studyInfo = MutableLiveData<StudyInfoRsp>()
    private val _studyList = MutableLiveData<StudiedRsp>()
    private val _boughtList = MutableLiveData<BoughtRsp>()

    override val liveStudyInfo: LiveData<StudyInfoRsp> = _studyInfo
    override val liveStudyList: LiveData<StudiedRsp> = _studyList
    override val liveBoughtList: LiveData<BoughtRsp> = _boughtList

    /**
     * 学习情况
     */
    override suspend fun getStudyInfo() {
        service.getStudyInfo()
            .serverData()
            .onSuccess {
                onBizzOK<StudyInfoRsp> { code, data, message ->
                    _studyInfo.value = data
                    LogUtils.i("获取学习信息 BizOK $data")
                }
                onBizzError { code, message ->
                    _studyInfo.value = null
                    LogUtils.w("获取学习信息 BizError $code,$message")
                }
            }
            .onFailure {
                _studyInfo.value = null
                LogUtils.e("获取学习信息 接口异常 ${it.message}")
            }
    }

    /**
     * 最近学习列表
     */
    override suspend fun getStudyList() {
        service.getStudyList()
            .serverData()
            .onSuccess {
                //只要不是接口响应成功，
                onBizzError { code, message ->
                    LogUtils.w("获取学习过的课程列表 BizError $code,$message")
                }
                onBizzOK<StudiedRsp> { code, data, message ->
                    _studyList.value = data?.apply {
                        datas?.forEach {
                            if (it.img_url?.startsWith("/") == true) {
                                it.img_url = "${getBaseHost()}${it.img_url}"
                            }
                        }
                    }
                    LogUtils.i("获取学习过的课程列表 BizOK $data")
                    return@onBizzOK
                }
            }.onFailure {
                LogUtils.e("获取学习过的课程列表 接口异常 ${it.message}")
            }
    }

    /**
     * 购买的课程
     */
    override suspend fun getBoughtCourse() {

        service.getBoughtCourse().serverData()
            .onSuccess {
                //只要不是接口响应成功，
                onBizzError { code, message ->
                    _boughtList.value = null
                    LogUtils.w("获取购买的课程 BizError $code,$message")
                }
                onBizzOK<BoughtRsp> { code, data, message ->
                    _boughtList.value = data
                    LogUtils.i("获取购买的课程 BizOK $data")
                    return@onBizzOK
                }
            }.onFailure {
                _boughtList.value = null
                LogUtils.e("获取购买的课程 接口异常 ${it.message}")
            }

    }

    /**
     * 将studyPageSource转化为flow数据
     */
    override suspend fun pagingData(): Flow<PagingData<StudiedRsp.Data>> {
        val config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 5,
            initialLoadSize = 10,
            maxSize = pageSize * 3
        )
        return Pager(config = config, initialKey = null) {
            StudyItemPagingSource(service)
        }.flow

    }

    private val _livePermission = SingleLiveData<HasCoursePermission>()
    private val _liveChapterList = SingleLiveData<ChapterListRsp>()
    private val _livePlayCourse = SingleLiveData<PlayCourseRsp>()


    override val livePermissionResult: LiveData<HasCoursePermission> = _livePermission
    override val liveChapterList: LiveData<ChapterListRsp> = _liveChapterList
    override val livePlayCourse: SingleLiveData<PlayCourseRsp> = _livePlayCourse

    override suspend fun hasPermission(courseId: Int) {
        service.getCoursePermission(courseId)
            .serverData()
            .onSuccess {
                //只要不是接口响应成功，
                onBizzError { code, message ->
                    LogUtils.w("学习权限 BizError $code,$message")
                }
                onBizzOK<HasCoursePermission> { code, data, message ->
                    _livePermission.value = data
                    LogUtils.i("学习权限 BizOK $data")
                    return@onBizzOK
                }
            }.onFailure {
                LogUtils.e("学习权限 接口异常 ${it.message}")
            }
    }

    override suspend fun getChapters(courseId: Int) {
        service.getCourseChapter(courseId)
            .serverData()
            .onSuccess {
                //只要不是接口响应成功，
                onBizzError { code, message ->
                    LogUtils.w("课时章节 BizError $code,$message")
                }
                onBizzOK<ChapterListRsp> { code, data, message ->
                    _liveChapterList.value = data
                    LogUtils.i("课时章节 BizOK $data")
                    return@onBizzOK
                }
            }.onFailure {
                LogUtils.e("课时章节 接口异常 ${it.message}")
            }
    }

    override suspend fun getPlayInfo(key: String) {
        service.getCoursePlayUrl(key)
            .serverData()
            .onSuccess {
                //只要不是接口响应成功，
                onBizzError { code, message ->
                    LogUtils.w("课时播放信息 BizError $code,$message")
                }
                onBizzOK<PlayCourseRsp> { code, data, message ->
                    _livePlayCourse.value = data
                    LogUtils.i("课时播放信息 BizOK $data")
                    return@onBizzOK
                }
            }.onFailure {
                LogUtils.e("课时播放信息 接口异常 ${it.message}")
            }
    }

}

/**
 * 处理分页逻辑
 */
class StudyItemPagingSource(val service: StudyService) : PagingSource<Int, StudiedRsp.Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StudiedRsp.Data> {
        var result: LoadResult<Int, StudiedRsp.Data> =
            LoadResult.Error<Int, StudiedRsp.Data>(Exception("加载中..."))
        var firstPage = params.key ?: 1
        service.getStudyList(firstPage, params.loadSize)
            .serverData()
            .onSuccess {
                onBizzError { code, message ->
                    LogUtils.w("获取学习过的课程列表 BizError $code,$message")
                    result = LoadResult.Error(Exception(message))
                }
                onBizzOK<StudiedRsp> { code, data, message ->
                    LogUtils.i("获取学习过的课程列表 BizOK $data")
                    val totalPage = data?.total_page ?: 0
                    //加载下一页的key 如果传null就说明到底了
                    val nextPage = if (firstPage < totalPage) firstPage++ else null
                    result = LoadResult.Page<Int, StudiedRsp.Data>(
                        data?.datas ?: emptyList(),
                        null,
                        nextPage
                    )
                }
            }
            .onFailure {
                LogUtils.e("获取学习过的课程列表 接口异常 ${it.message}")
                result = LoadResult.Error(it)
            }
        return result

    }
}