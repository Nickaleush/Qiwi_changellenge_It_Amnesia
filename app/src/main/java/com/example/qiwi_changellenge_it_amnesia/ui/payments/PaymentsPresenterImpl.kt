package com.example.qiwi_changellenge_it_amnesia.ui.payments

import android.annotation.SuppressLint
import com.example.qiwi_changellenge_it_amnesia.domain.mainApi.MainApi
import com.example.qiwi_changellenge_it_amnesia.domain.models.Payment
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PaymentsPresenterImpl @Inject constructor(private val mainApi: MainApi) : BasePresenterImpl<PaymentsView>(),
    PaymentsPresenter {

    override lateinit var view: PaymentsView

    @SuppressLint("CheckResult")
    override fun getPurchaseList() {
        mainApi.getPurchaseList()
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe({
                view.hideSkeleton()
                view.initRecyclerViewPurchaseList(it)
                // Log.d("01333", it.toString())
            },{
                view.showError(it.message)
            })
    }

    @SuppressLint("CheckResult")
    override fun getSalesList() {
        mainApi.getSalesList()
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe({
                view.hideSkeleton()
                view.initRecyclerViewPurchaseList(it)
                // Log.d("01333", it.toString())
            },{
                view.showError(it.message)
            })
    }

    override fun start() = Unit
}