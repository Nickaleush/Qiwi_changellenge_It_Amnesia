package com.example.qiwi_changellenge_it_amnesia.di

import android.content.Context
import android.content.SharedPreferences
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.di.components.*
import com.example.qiwi_changellenge_it_amnesia.di.modules.LocalDataModule
import com.example.qiwi_changellenge_it_amnesia.di.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        LocalDataModule::class,
        NetworkModule::class,
    ]
)

interface AppComponent {

    fun inject(application: App)

    fun context(): Context

    fun provideSharedPreferences(): SharedPreferences

    fun createMainActivity(): MainComponent

    fun createAuthFragment(): AuthComponent

    fun createQRFragment():QRFragmentComponent

}