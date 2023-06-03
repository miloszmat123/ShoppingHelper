package com.example.shoppinghelper.data

import android.app.Application

class ContainerApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}