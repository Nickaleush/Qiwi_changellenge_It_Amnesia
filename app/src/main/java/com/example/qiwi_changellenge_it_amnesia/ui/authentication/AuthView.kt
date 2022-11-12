package com.example.qiwi_changellenge_it_amnesia.ui.authentication

import com.example.qiwi_changellenge_it_amnesia.mvp.BaseView

interface AuthView : BaseView {
    fun showError(message: String?)
    fun navToUserProfileFragment()
    fun showConfirmationDialog()
}