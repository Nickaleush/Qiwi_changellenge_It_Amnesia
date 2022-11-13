package com.example.qiwi_changellenge_it_amnesia.ui.payments

import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenter

interface PaymentsPresenter: BasePresenter {
    var view: PaymentsView
    fun getPurchaseList()
    fun getSalesList()
}