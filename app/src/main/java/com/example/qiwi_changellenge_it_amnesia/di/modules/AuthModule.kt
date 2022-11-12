package com.example.qiwi_changellenge_it_amnesia.di.modules

import com.example.qiwi_changellenge_it_amnesia.di.scopes.AuthScope
import com.example.qiwi_changellenge_it_amnesia.ui.authentication.AuthPresenter
import com.example.qiwi_changellenge_it_amnesia.ui.authentication.AuthPresenterImpl
import dagger.Module

@Module
interface AuthModule {
    @AuthScope
    fun presenter(presenter: AuthPresenterImpl): AuthPresenter
}