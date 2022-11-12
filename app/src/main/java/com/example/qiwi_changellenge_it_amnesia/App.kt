package com.example.qiwi_changellenge_it_amnesia

import android.app.Application
import android.content.Context
import com.example.qiwi_changellenge_it_amnesia.di.AppModule
import com.example.qiwi_changellenge_it_amnesia.di.AppComponent
import com.example.qiwi_changellenge_it_amnesia.di.DaggerAppComponent

class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        setUpDagger()
    }

    fun getAppComponent(): AppComponent = appComponent

    fun getContext(): Context? {
        return instance.applicationContext
    }

    private fun setUpDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext, this))
            .build()
        appComponent.inject(this)
    }

    companion object {
        lateinit var instance: App private set
    }
}