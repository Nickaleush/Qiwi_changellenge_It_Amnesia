package com.example.qiwi_changellenge_it_amnesia.ui.readQR

import com.example.qiwi_changellenge_it_amnesia.mvp.BaseView

interface ReadQRView : BaseView {
    fun showError(message: String?)
}