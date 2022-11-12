package com.example.qiwi_changellenge_it_amnesia.ui.qr

import com.example.qiwi_changellenge_it_amnesia.domain.models.Code
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenter

interface QRPresenter: BasePresenter {
    var view: QRFragmentView
    fun sendPaymentConfirmation()
    fun confirmPayment(code: Code)
}