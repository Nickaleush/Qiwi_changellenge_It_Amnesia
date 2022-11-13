package com.example.qiwi_changellenge_it_amnesia.ui.profile

import android.annotation.SuppressLint
import com.example.qiwi_changellenge_it_amnesia.domain.mainApi.MainApi
import com.example.qiwi_changellenge_it_amnesia.domain.models.ShopName
import com.example.qiwi_changellenge_it_amnesia.domain.sharedPreferences.SharedPreferences
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl
import com.example.qiwi_changellenge_it_amnesia.ui.profile.ProfileFragment.Companion.SHOP_OPENED
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfilePresenterImpl @Inject constructor(private val mainApi: MainApi) : BasePresenterImpl<ProfileView>(),
    ProfilePresenter {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override lateinit var view: ProfileView

    override fun start() = Unit

    @SuppressLint("CheckResult")
    override fun createShopAccount(shopName: ShopName) {
        mainApi.createShopAccount(shopName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                sharedPreferences.accessToken = it.token
                view.closeBottomSheetDialog()
                SHOP_OPENED = true
                view.updateData()
            }, {
                SHOP_OPENED = false
                view.showError(it.message)
            })
    }
}