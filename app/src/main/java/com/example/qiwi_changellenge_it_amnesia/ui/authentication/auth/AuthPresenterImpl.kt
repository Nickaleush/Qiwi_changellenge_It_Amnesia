package com.example.qiwi_changellenge_it_amnesia.ui.authentication.auth

import android.annotation.SuppressLint
import android.util.Log
import com.example.qiwi_changellenge_it_amnesia.domain.authorizationApi.AuthorizationApi
import com.example.qiwi_changellenge_it_amnesia.domain.models.BuyerToLogin
import com.example.qiwi_changellenge_it_amnesia.domain.models.SellerToLogin
import com.example.qiwi_changellenge_it_amnesia.domain.sharedPreferences.SharedPreferences
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthPresenterImpl @Inject constructor(private val authorizationApi: AuthorizationApi) : BasePresenterImpl<AuthView>(),
    AuthPresenter {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override lateinit var view: AuthView

    override fun start() = Unit


    @SuppressLint("CheckResult")
    override fun loginBuyerWithPhone(userBuyer: BuyerToLogin) {
        authorizationApi.loginBuyerWithPhone(userBuyer)
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe({
                sharedPreferences.accessToken = it.accessToken
                view.showProgressBar()
                view.navToUserProfileFragment()
                Log.d("9999", it.accessToken)
            }, {
                view.showError(it.message)
            })
    }

    @SuppressLint("CheckResult")
    override fun loginSellerWithPhone(userSeller: SellerToLogin) {
        authorizationApi.loginSellerWithPhone(userSeller)
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe({
                sharedPreferences.accessToken = it.accessToken
                view.showProgressBar()
                view.navToUserProfileFragment()
                Log.d("9999", it.accessToken)
            }, {
                view.showError(it.message)
            })
    }


}