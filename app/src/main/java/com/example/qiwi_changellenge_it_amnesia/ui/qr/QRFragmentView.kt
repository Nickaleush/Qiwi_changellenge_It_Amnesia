package com.example.qiwi_changellenge_it_amnesia.ui.qr

import com.example.qiwi_changellenge_it_amnesia.mvp.BaseView

interface QRFragmentView: BaseView {
    fun showError(message: String?)
    fun startConfirmationCreateQRCode()
    fun showUserCodeError()
    fun showProgressDialog()
    fun closeConfirmAndDrawQR(paymentToken: String)
}