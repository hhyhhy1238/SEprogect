package com.example.client.ui

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        lateinit var instance: Application
    }//全局上下文
    init {
        instance = this
    }
}

val appContext: Context = App.instance