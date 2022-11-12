package com.example.qiwi_changellenge_it_amnesia.di.modules

import com.example.qiwi_changellenge_it_amnesia.di.scopes.QRFragmentScope
import com.example.qiwi_changellenge_it_amnesia.ui.qr.QRFragmentPresenter
import com.example.qiwi_changellenge_it_amnesia.ui.qr.QRFragmentPresenterImpl
import dagger.Module

@Module
interface QRFragmentModule {
    @QRFragmentScope
    fun presenter(presenter: QRFragmentPresenterImpl): QRFragmentPresenter
}