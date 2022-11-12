package com.example.qiwi_changellenge_it_amnesia.di.modules

import com.example.qiwi_changellenge_it_amnesia.di.scopes.QRScope
import com.example.qiwi_changellenge_it_amnesia.ui.qr.QRPresenter
import com.example.qiwi_changellenge_it_amnesia.ui.qr.QRPresenterImpl
import dagger.Module

@Module
interface QRModule {
    @QRScope
    fun presenter(presenter: QRPresenterImpl): QRPresenter
}