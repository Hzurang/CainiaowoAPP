package com.example.mine

import android.renderscript.ScriptGroup
import com.example.common.network.KtRetrofit
import com.example.mine.net.MineService
import com.example.mine.repo.IMineResource
import com.example.mine.repo.MineResource
import com.example.mine.ui.MineViewModel
import com.example.common.utils.getBaseHost
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

val mineModules = module {
    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }.getService(MineService::class.java)
    }
    single { MineResource(get()) } bind IMineResource::class


    viewModel { MineViewModel(get()) }
}