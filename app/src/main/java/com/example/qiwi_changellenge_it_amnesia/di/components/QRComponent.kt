package com.example.qiwi_changellenge_it_amnesia.di.components

import com.example.qiwi_changellenge_it_amnesia.di.modules.QRModule
import com.example.qiwi_changellenge_it_amnesia.di.scopes.MainScope
import com.example.qiwi_changellenge_it_amnesia.ui.qr.QRFragment
import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [QRModule::class])
interface QRComponent {
    fun inject(fragment: QRFragment)
}