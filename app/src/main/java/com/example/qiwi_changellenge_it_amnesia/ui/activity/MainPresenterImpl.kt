package com.example.qiwi_changellenge_it_amnesia.ui.activity

import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl
import com.example.qiwi_changellenge_it_amnesia.di.scopes.MainScope
import javax.inject.Inject

@MainScope
class MainPresenterImpl  @Inject constructor() : BasePresenterImpl<MainView>(), MainPresenter {

    override fun start() = Unit
    override lateinit var view: MainView

}