package com.soft.eventos.di

import android.app.Application
import com.soft.eventos.di.AppModules.Project
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseAplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseAplication)
            modules(Project)
        }
    }
}