package com.example.study.ui.play

import com.example.common.base.BaseViewModel
import com.example.study.repo.IStudyResource


class PlayViewModel(val repo: IStudyResource) : BaseViewModel() {

    val livePermissionResult = repo.livePermissionResult
    val liveChapterList = repo.liveChapterList
    val livePlayCourse = repo.livePlayCourse

    fun hasPermission(courseId: Int) = serverAwait { repo.hasPermission(courseId) }

    fun getChapters(courseId: Int) = serverAwait { repo.getChapters(courseId) }

    fun getPlayInfo(key: String) = serverAwait {
        repo.getPlayInfo(key)
    }
}