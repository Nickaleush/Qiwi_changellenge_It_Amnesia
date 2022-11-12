package com.example.qiwi_changellenge_it_amnesia.ui.authentication.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.domain.sharedPreferences.SharedPreferences
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import javax.inject.Inject

class AuthFragment :  BaseFragment<AuthPresenterImpl>(), AuthView {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createAuthFragment()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.auth_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
        presenter.view = this


    }

    override fun onBackPressed() {
        requireActivity().finish()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    override fun navToUserProfileFragment() {

    }

    override fun showProgressBar() {
        //progressDialog.start()
    }

    companion object {
//        const val phoneAuthKey = "USER_NUMBER_KEY"
//        private var LOGIN_WITH_PHONE = false
//        var NAV_BACK_PRESSED = false
//        private var savedAuthPhoneCode: String? = "+7"
//        private var savedAuthPhone: String? = null
//        var OPEN_AUTH_FRAGMENT = false
//        var PHONE_RECOVERY = false
    }
}