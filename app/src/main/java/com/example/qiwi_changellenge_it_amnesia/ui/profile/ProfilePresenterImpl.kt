package com.example.qiwi_changellenge_it_amnesia.ui.profile

import com.example.qiwi_changellenge_it_amnesia.domain.mainApi.MainApi
import com.example.qiwi_changellenge_it_amnesia.mvp.BasePresenterImpl
import javax.inject.Inject

class ProfilePresenterImpl @Inject constructor(private val mainApi: MainApi) : BasePresenterImpl<ProfileView>(),
    ProfilePresenter {

    override lateinit var view: ProfileView

    override fun start() = Unit
}