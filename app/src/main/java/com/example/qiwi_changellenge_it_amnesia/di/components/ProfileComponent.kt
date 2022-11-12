package com.example.qiwi_changellenge_it_amnesia.di.components

import com.example.qiwi_changellenge_it_amnesia.di.modules.ProfileModule
import com.example.qiwi_changellenge_it_amnesia.di.scopes.ProfileScope
import com.example.qiwi_changellenge_it_amnesia.ui.profile.ProfileFragment
import dagger.Subcomponent

@ProfileScope
@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {
    fun inject(fragment: ProfileFragment)
}