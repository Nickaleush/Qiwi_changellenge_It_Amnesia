package com.example.qiwi_changellenge_it_amnesia.di.components

import com.example.qiwi_changellenge_it_amnesia.di.modules.AuthModule
import com.example.qiwi_changellenge_it_amnesia.di.scopes.AuthScope
import com.example.qiwi_changellenge_it_amnesia.ui.authentication.auth.AuthFragment
import dagger.Subcomponent

@AuthScope
@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {
    fun inject(fragment: AuthFragment)
}