package com.example.qiwi_changellenge_it_amnesia.di

import android.content.Context
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.di.components.MainComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)

interface AppComponent {

    fun inject(application: App)

    fun context(): Context

    fun createMainActivity(): MainComponent

}