package com.example.service.assistant

import android.app.Application
import com.didichuxing.doraemonkit.DoraemonKit
import com.didichuxing.doraemonkit.kit.AbstractKit

object AssistantApp {
    fun initConfig(application: Application){
        DoraemonKit.install(application, mutableListOf<AbstractKit>(ServiceHostKit()),"cainiao")
    }
}