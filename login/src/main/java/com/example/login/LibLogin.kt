package com.example.login

import com.example.common.network.KtRetrofit
import com.example.login.net.LoginService
import com.example.login.repo.ILoginResource
import com.example.login.repo.LoginRepository
import com.example.common.utils.getBaseHost
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val loginModules = module {

    //service retrofit
    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }.getService(LoginService::class.java)
    }
    // respository
    single {
        LoginRepository(get())
    } bind ILoginResource::class

    // viewModel
    viewModel { LoginViewModel(get()) }
}