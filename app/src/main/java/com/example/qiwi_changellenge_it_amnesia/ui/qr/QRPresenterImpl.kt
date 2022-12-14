package com.example.qiwi_changellenge_it_amnesia.ui.qr

import android.annotation.SuppressLint
import com.example.qiwi_changellenge_it_amnesia.domain.mainApi.MainApi
import com.example.qiwi_changellenge_it_amnesia.domain.models.Code
import com.example.qiwi_changellenge_it_amnesia.domain.models.UserToLogin
import com.example.qiwi_changellenge_it_amnesia.domain.sharedPreferences.SharedPreferences
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class QRPresenterImpl @Inject constructor(private val mainApi: MainApi) : BasePresenterImpl<QRFragmentView>(), QRPresenter {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override lateinit var view: QRFragmentView

    override fun start() = Unit

    @SuppressLint("CheckResult")
    override fun sendPaymentConfirmation() {
        mainApi.sendPaymentConfirmation()
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe({
                view.startConfirmationCreateQRCode()
                view.hideProgressDialog()
            }, {
                view.showError(it.message)
                view.hideProgressDialog()
            })
    }

    @SuppressLint("CheckResult")
    override fun confirmPayment(code: Code) {
        mainApi.confirmPayment(code)
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe({
                view.showUserCodeError()
                view.closeConfirmAndDrawQR(it.token)
                view.hideProgressDialog()
            }, {
                view.showError(it.message)
                view.hideProgressDialog()
            })
    }
}