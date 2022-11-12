package com.example.qiwi_changellenge_it_amnesia.di.modules

import com.example.qiwi_changellenge_it_amnesia.di.scopes.ProfileScope
import com.example.qiwi_changellenge_it_amnesia.ui.profile.ProfilePresenter
import com.example.qiwi_changellenge_it_amnesia.ui.profile.ProfilePresenterImpl
import dagger.Module

@Module
interface ProfileModule {
    @ProfileScope
    fun presenter(presenter: ProfilePresenterImpl): ProfilePresenter
}