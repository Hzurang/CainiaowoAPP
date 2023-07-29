package com.example.cainiaowo

import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.BaseApplication
import com.example.common.BuildConfig
import com.example.common.ktx.application
import com.example.course.courseModules
import com.example.home.homeModules
import com.example.login.loginModules
import com.example.mine.mineModules
import com.example.study.studyModules
import com.example.service.assistant.AssistantApp
import com.example.service.serviceModules
import org.koin.core.context.loadKoinModules

class MyApplication : BaseApplication() {
    private val modules = listOf(loginModules, homeModules,courseModules,studyModules, serviceModules, mineModules)

    override fun initConfig() {
        super.initConfig()
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog()     // Print log
            ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application
        AssistantApp.initConfig(application)

        loadKoinModules(modules)
    }
}