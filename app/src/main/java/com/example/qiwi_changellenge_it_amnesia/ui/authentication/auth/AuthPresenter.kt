package com.example.qiwi_changellenge_it_amnesia.ui.authentication.auth

import com.example.qiwi_changellenge_it_amnesia.domain.models.BuyerToLogin
import com.example.qiwi_changellenge_it_amnesia.domain.models.SellerToLogin
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenter

interface AuthPresenter: BasePresenter {
    var view: AuthView
    fun loginBuyerWithPhone(userBuyer: BuyerToLogin)
    fun loginSellerWithPhone(userSeller: SellerToLogin)
}