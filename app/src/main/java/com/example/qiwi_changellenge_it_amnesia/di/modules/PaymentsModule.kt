package com.example.qiwi_changellenge_it_amnesia.di.modules

import com.example.qiwi_changellenge_it_amnesia.di.scopes.PaymentsScope
import com.example.qiwi_changellenge_it_amnesia.ui.payments.PaymentsPresenter
import com.example.qiwi_changellenge_it_amnesia.ui.payments.PaymentsPresenterImpl
import dagger.Module

@Module
interface PaymentsModule {
    @PaymentsScope
    fun presenter(presenter: PaymentsPresenterImpl): PaymentsPresenter
}