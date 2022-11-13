package com.example.qiwi_changellenge_it_amnesia.ui.payments

import com.example.qiwi_changellenge_it_amnesia.domain.models.Payment
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseView
import java.util.ArrayList

interface PaymentsView : BaseView {
    fun showError(message: String?)
    fun initRecyclerViewPurchaseList(purchaseList: ArrayList<Payment>)
    fun hideSkeleton()
}