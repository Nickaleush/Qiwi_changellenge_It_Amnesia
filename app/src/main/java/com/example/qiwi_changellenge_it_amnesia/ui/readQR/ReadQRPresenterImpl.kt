package com.example.qiwi_changellenge_it_amnesia.ui.readQR

import com.example.qiwi_changellenge_it_amnesia.domain.mainApi.MainApi
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl
import javax.inject.Inject

class ReadQRPresenterImpl @Inject constructor(private val mainApi: MainApi) : BasePresenterImpl<ReadQRView>(),
    ReadQRPresenter {

    override lateinit var view: ReadQRView

    override fun start() = Unit
}