package com.example.qiwi_changellenge_it_amnesia.di.components

import com.example.qiwi_changellenge_it_amnesia.di.modules.PaymentsModule
import com.example.qiwi_changellenge_it_amnesia.di.scopes.ProfileScope
import com.example.qiwi_changellenge_it_amnesia.ui.payments.PaymentsFragment
import dagger.Subcomponent

@ProfileScope
@Subcomponent(modules = [PaymentsModule::class])
interface PaymentsComponent {
    fun inject(fragment: PaymentsFragment)
}