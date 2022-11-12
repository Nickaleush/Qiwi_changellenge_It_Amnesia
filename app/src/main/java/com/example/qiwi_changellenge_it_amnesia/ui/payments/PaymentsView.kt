package com.example.qiwi_changellenge_it_amnesia.ui.payments

import com.example.qiwi_changellenge_it_amnesia.mvp.BaseView

interface PaymentsView : BaseView {
    fun showError(message: String?)
}