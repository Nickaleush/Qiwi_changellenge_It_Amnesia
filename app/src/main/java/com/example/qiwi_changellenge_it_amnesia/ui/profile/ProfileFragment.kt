package com.example.qiwi_changellenge_it_amnesia.ui.profile

import android.os.Bundle
import android.view.View
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}