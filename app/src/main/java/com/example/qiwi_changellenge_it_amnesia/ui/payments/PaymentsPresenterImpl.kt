package com.example.qiwi_changellenge_it_amnesia.ui.payments

import com.example.qiwi_changellenge_it_amnesia.domain.mainApi.MainApi
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl
import javax.inject.Inject

class PaymentsPresenterImpl @Inject constructor(private val mainApi: MainApi) : BasePresenterImpl<PaymentsView>(),
    PaymentsPresenter {

    override lateinit var view: PaymentsView

    override fun start() = Unit
}