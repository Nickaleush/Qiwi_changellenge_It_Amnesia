package com.example.qiwi_changellenge_it_amnesia.di.modules

import com.example.qiwi_changellenge_it_amnesia.ui.activity.MainPresenter
import com.example.qiwi_changellenge_it_amnesia.ui.activity.MainPresenterImpl
import com.example.qiwi_changellenge_it_amnesia.di.scopes.MainScope
import dagger.Module

@Module
interface MainModule {
    @MainScope
    fun presenter(presenter: MainPresenterImpl): MainPresenter
}