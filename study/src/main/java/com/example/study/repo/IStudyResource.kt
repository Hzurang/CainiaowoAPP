package com.example.study.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.common.model.SingleLiveData
import com.example.study.net.*
import kotlinx.coroutines.flow.Flow

interface IStudyResource {

    val liveStudyInfo: LiveData<StudyInfoRsp>
    val liveStudyList: LiveData<StudiedRsp>
    val liveBoughtList: LiveData<BoughtRsp>

    val livePermissionResult: LiveData<HasCoursePermission>
    val liveChapterList: LiveData<ChapterListRsp>
    val livePlayCourse: SingleLiveData<PlayCourseRsp>

    /**
     * 学习情况
     */
    suspend fun getStudyInfo()

    /**
     * 最近学习列表
     */
    suspend fun getStudyList()

    /**
     * 购买的课程
     */
    suspend fun getBoughtCourse()

    /**
     * 将studyPageSource转化为flow数据
     */
    suspend fun pagingData(): Flow<PagingData<StudiedRsp.Data>>

    /**
     * 根据课程id查询该用户是否有权限看课程
     */
    suspend fun hasPermission(courseId: Int)

    /**
     * 根据课程id，获取课程的章节课时
     */
    suspend fun getChapters(courseId: Int)

    /**
     * 根据章节课时里面的key，获取对应的视频播放信息，用于播放
     */
    suspend fun getPlayInfo(key: String)
}