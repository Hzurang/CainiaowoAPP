package com.example.mine

import android.app.Application
import com.example.common.BaseApplication
import com.example.service.serviceModules
import org.koin.core.context.loadKoinModules

class MineApplication:BaseApplication() {
    private val modules = listOf(serviceModules,mineModules)
    override fun initConfig() {
        super.initConfig()
        loadKoinModules(modules)
    }
}