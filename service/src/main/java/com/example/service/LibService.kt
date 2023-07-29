package com.example.service

import com.example.common.network.KtRetrofit
import org.koin.dsl.module

val serviceModules = module {
    //自定义参数类型
    single { (host: String) -> KtRetrofit.initConfig(host) }
}