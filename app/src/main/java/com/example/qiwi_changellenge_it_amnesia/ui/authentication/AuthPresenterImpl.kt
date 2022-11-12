package com.example.qiwi_changellenge_it_amnesia.ui.authentication

import android.annotation.SuppressLint
import com.example.qiwi_changellenge_it_amnesia.domain.authorizationApi.AuthorizationApi
import com.example.qiwi_changellenge_it_amnesia.domain.mainApi.MainApi
import com.example.qiwi_changellenge_it_amnesia.domain.models.Code
import com.example.qiwi_changellenge_it_amnesia.domain.models.UserToLogin
import com.example.qiwi_changellenge_it_amnesia.domain.models.UserToSignUp
import com.example.qiwi_changellenge_it_amnesia.domain.sharedPreferences.SharedPreferences
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthPresenterImpl @Inject constructor(private val authorizationApi: AuthorizationApi, private val mainApi: MainApi) : BasePresenterImpl<AuthView>(),
    AuthPresenter {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override lateinit var view: AuthView

    @SuppressLint("CheckResult")
    override fun loginWithPhone(user: UserToLogin) {
        authorizationApi.loginWithPhone(user)
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe({
                sharedPreferences.accessToken = it.token
                view.navToUserProfileFragment()
            }, {
                view.showError(it.message)
            })
    }

    @SuppressLint("CheckResult")
    override fun signUpWithPhone(user: UserToSignUp) {
        authorizationApi.signUpWithPhone(user)
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe({
                sharedPreferences.accessToken = it.token
                view.showConfirmationDialog()
            }, {
                view.showError(it.message)
            })
    }

    @SuppressLint("CheckResult")
    override fun confirmAccount(code: Code) {
        mainApi.confirmAccount(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                sharedPreferences.accessToken = it.token
                view.navToUserProfileFragment()
            }, {
                view.showError(it.message)
            })
    }

    override fun start() = Unit

}