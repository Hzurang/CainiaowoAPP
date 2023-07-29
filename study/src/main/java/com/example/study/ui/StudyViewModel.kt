package com.example.study.ui

import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.common.base.BaseViewModel
import com.example.study.net.BoughtRsp
import com.example.study.net.StudiedRsp
import com.example.study.net.StudyInfoRsp
import com.example.study.repo.StudyResource
import com.example.service.repo.UserInfo

class StudyViewModel(private val resource: StudyResource) : BaseViewModel() {
    //学习页面的数据
    val liveStudyInfo: LiveData<StudyInfoRsp> = resource.liveStudyInfo
    val liveStudyList: LiveData<StudiedRsp> = resource.liveStudyList
    val liveBoughtList: LiveData<BoughtRsp> = resource.liveBoughtList
    //用户信息
    val obUserInfo = ObservableField<UserInfo>()

    fun getStudyData() = serverAwait {
        resource.getStudyInfo()
        resource.getStudyList()
        resource.getBoughtCourse()
    }

    suspend fun pagingData()= resource.pagingData().asLiveData()
}