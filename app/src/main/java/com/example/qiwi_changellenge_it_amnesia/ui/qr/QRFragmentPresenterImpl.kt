package com.example.qiwi_changellenge_it_amnesia.ui.qr

import com.example.qiwi_changellenge_it_amnesia.domain.mainApi.MainApi
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl
import javax.inject.Inject

class QRFragmentPresenterImpl @Inject constructor(private val mainApi: MainApi) : BasePresenterImpl<QRFragmentView>(), QRFragmentPresenter {

    override lateinit var view: QRFragmentView

    override fun start() = Unit

}