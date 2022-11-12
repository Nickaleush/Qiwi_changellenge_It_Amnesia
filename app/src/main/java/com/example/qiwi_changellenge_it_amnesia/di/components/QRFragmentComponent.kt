package com.example.qiwi_changellenge_it_amnesia.di.components

import com.example.qiwi_changellenge_it_amnesia.di.modules.QRFragmentModule
import com.example.qiwi_changellenge_it_amnesia.di.scopes.MainScope
import com.example.qiwi_changellenge_it_amnesia.ui.qr.QRFragment
import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [QRFragmentModule::class])
interface QRFragmentComponent {
    fun inject(fragment: QRFragment)
}