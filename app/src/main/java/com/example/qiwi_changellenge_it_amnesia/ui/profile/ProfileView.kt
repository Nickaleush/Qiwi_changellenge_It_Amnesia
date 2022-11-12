package com.example.qiwi_changellenge_it_amnesia.ui.profile

import com.example.qiwi_changellenge_it_amnesia.mvp.BaseView

interface ProfileView : BaseView {
    fun showError(message: String?)
}