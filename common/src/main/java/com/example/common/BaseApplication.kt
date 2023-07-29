package com.example.common

import android.app.Application
import android.content.Context
import com.example.common.exception.CrashHandler
import me.jessyan.autosize.AutoSize
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

// 抽象类BaseApplication
abstract class BaseApplication : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initConfig()
        initData()
    }

    /**
     * 配置初始化，可重写方法
     */
    protected open fun initConfig() {
        startKoin {
            androidLogger(level = Level.ERROR) //log level Error方能保证这句话不会报错
            androidContext(this@BaseApplication)
        }
        //今日头条适配方案：当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
        AutoSize.initCompatMultiProcess(this)
        CrashHandler.getInstance().init(this)
    }

    /**
     * 数据初始化
     */
    protected open fun initData() {

    }
}