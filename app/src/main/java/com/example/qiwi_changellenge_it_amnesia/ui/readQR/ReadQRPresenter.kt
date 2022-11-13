package com.example.qiwi_changellenge_it_amnesia.ui.readQR

import com.example.qiwi_changellenge_it_amnesia.domain.models.PaymentBody
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenter

interface ReadQRPresenter: BasePresenter {
    var view: ReadQRView
    fun sendTransaction(paymentBody: PaymentBody)
}