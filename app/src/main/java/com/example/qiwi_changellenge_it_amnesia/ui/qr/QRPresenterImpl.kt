package com.example.qiwi_changellenge_it_amnesia.ui.qr

import com.example.qiwi_changellenge_it_amnesia.domain.mainApi.MainApi
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl
import javax.inject.Inject

class QRPresenterImpl @Inject constructor(private val mainApi: MainApi) : BasePresenterImpl<QRFragmentView>(), QRPresenter {

    override lateinit var view: QRFragmentView

    override fun start() = Unit

}