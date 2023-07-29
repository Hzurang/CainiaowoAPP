package com.example.study

import com.example.common.network.KtRetrofit
import com.example.common.utils.getBaseHost
import com.example.study.net.StudyService
import com.example.study.repo.IStudyResource
import com.example.study.repo.StudyResource
import com.example.study.ui.StudyViewModel
import com.example.study.ui.play.PlayViewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind

val studyModules = module {
    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }
            .getService(StudyService::class.java)
    }

    single {
        StudyResource(get())
    } bind IStudyResource::class

    viewModel {
        PlayViewModel(get())
    }

    viewModel {
        StudyViewModel(get())
    }
}