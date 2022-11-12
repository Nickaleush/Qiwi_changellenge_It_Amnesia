package com.example.qiwi_changellenge_it_amnesia.ui.profile

import android.widget.Toast
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment

class ProfileFragment: BaseFragment<ProfilePresenterImpl>(), ProfileView {

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createProfileFragment()
            .inject(this)
    }

    override fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}