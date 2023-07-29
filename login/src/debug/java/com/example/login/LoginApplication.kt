package com.example.login

import com.example.common.BaseApplication
import org.koin.core.context.loadKoinModules

class LoginApplication:BaseApplication() {
    override fun initConfig() {
        super.initConfig()
        loadKoinModules(loginModules)
    }
}