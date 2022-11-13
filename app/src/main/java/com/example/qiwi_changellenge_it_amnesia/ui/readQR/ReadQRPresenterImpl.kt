package com.example.qiwi_changellenge_it_amnesia.ui.readQR

import android.annotation.SuppressLint
import com.example.qiwi_changellenge_it_amnesia.domain.mainApi.MainApi
import com.example.qiwi_changellenge_it_amnesia.domain.models.PaymentBody
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReadQRPresenterImpl @Inject constructor(private val mainApi: MainApi) : BasePresenterImpl<ReadQRView>(),
    ReadQRPresenter {

    override lateinit var view: ReadQRView

    override fun start() = Unit

    @SuppressLint("CheckResult")
    override fun sendTransaction(paymentBody: PaymentBody) {
        mainApi.sendTransaction(paymentBody)
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe({
                view.successPay()
                //view.startConfirmationCreateQRCode()
            }, {
                view.showError(it.message)
            })
    }
}