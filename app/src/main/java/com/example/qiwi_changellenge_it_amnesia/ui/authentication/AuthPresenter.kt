package com.example.qiwi_changellenge_it_amnesia.ui.authentication

import com.example.qiwi_changellenge_it_amnesia.domain.models.Code
import com.example.qiwi_changellenge_it_amnesia.domain.models.UserToLogin
import com.example.qiwi_changellenge_it_amnesia.domain.models.UserToSignUp
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenter

interface AuthPresenter: BasePresenter {
    var view: AuthView
    fun loginWithPhone(user: UserToLogin)
    fun signUpWithPhone(user: UserToSignUp)
    fun confirmAccount(code: Code)
}