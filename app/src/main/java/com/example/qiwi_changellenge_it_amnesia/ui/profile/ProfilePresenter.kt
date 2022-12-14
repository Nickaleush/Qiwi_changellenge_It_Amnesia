package com.example.qiwi_changellenge_it_amnesia.ui.profile

import com.example.qiwi_changellenge_it_amnesia.domain.models.ShopName
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenter

interface ProfilePresenter: BasePresenter {
    var view: ProfileView
    fun createShopAccount(shopName: ShopName)
}