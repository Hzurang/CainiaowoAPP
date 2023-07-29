package com.example.service.repo

import android.app.Application
import android.content.Context
import android.provider.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object DbHelper {
    /**
     * 以普通数据对象的形式，获取userInfo
     */
    fun getUserInfo(context: Context) = DataBase.getInstance(context).userDao().queryUser()

    /**
     * 获取room数据表中存储的userInfo
     * return liveData形式
     */
    fun getLiveUserInfo(context: Context) = DataBase.getInstance(context).userDao().queryLiveData()

    /**
     * 删除数据表中的userInfo信息
     */
    fun deleteUserInfo(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            getUserInfo(context)?.apply {
                DataBase.getInstance(context).userDao().deleteUser(this)
            }
        }
    }

    /**
     * 新增用户数据到数据表
     */
    fun insertUserInfo(context: Context, user: UserInfo) {
        GlobalScope.launch(Dispatchers.IO) {
            DataBase.getInstance(context).userDao().insertUser(user)
        }
    }

}