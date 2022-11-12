package com.example.qiwi_changellenge_it_amnesia.di.modules

import com.example.qiwi_changellenge_it_amnesia.di.scopes.ReadQRScope
import com.example.qiwi_changellenge_it_amnesia.ui.readQR.ReadQRPresenter
import com.example.qiwi_changellenge_it_amnesia.ui.readQR.ReadQRPresenterImpl
import dagger.Module

@Module
interface ReadQRModule {
    @ReadQRScope
    fun presenter(presenter: ReadQRPresenterImpl): ReadQRPresenter
}