package com.example.qiwi_changellenge_it_amnesia.di.components

import com.example.qiwi_changellenge_it_amnesia.ui.activity.MainActivity
import com.example.qiwi_changellenge_it_amnesia.di.modules.MainModule
import com.example.qiwi_changellenge_it_amnesia.di.scopes.MainScope
import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {
    fun inject(activity: MainActivity)
}