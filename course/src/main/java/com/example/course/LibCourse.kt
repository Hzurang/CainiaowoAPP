package com.example.course

import com.example.common.network.KtRetrofit
import com.example.course.net.CourseService
import com.example.course.repo.CourseReposity
import com.example.course.repo.ICourseReposity
import com.example.course.ui.CourseViewModel
import com.example.common.utils.getBaseHost
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module


val courseModules = module {
    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }
            .getService(CourseService::class.java)
    }

    single { CourseReposity(get()) } bind ICourseReposity::class


    viewModel { CourseViewModel(get()) }
}