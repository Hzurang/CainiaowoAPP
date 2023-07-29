package com.example.home

import com.example.common.network.KtRetrofit
import com.example.home.net.HomeService
import com.example.home.repo.HomeRepo
import com.example.home.repo.IHomeReposity
import com.example.home.ui.HomeViewModel
import com.example.common.utils.getBaseHost
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.get
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModules = module {
    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }.getService(HomeService::class.java)
    }

    single {
        HomeRepo(get())
    } bind IHomeReposity::class

    viewModel {
        HomeViewModel(get())
    }
}