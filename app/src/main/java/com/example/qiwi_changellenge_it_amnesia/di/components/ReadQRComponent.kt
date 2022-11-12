package com.example.qiwi_changellenge_it_amnesia.di.components

import com.example.qiwi_changellenge_it_amnesia.di.modules.ReadQRModule
import com.example.qiwi_changellenge_it_amnesia.di.scopes.MainScope
import com.example.qiwi_changellenge_it_amnesia.ui.readQR.ReadQRFragment
import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [ReadQRModule::class])
interface ReadQRComponent {
    fun inject(fragment: ReadQRFragment)
}