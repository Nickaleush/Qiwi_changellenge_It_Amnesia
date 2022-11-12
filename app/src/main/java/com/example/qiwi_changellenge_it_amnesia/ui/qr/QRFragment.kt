package com.example.qiwi_changellenge_it_amnesia.ui.qr

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment

class QRFragment: BaseFragment<QRFragmentPresenterImpl>(), QRFragmentView  {

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createQRFragment()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
        presenter.view = this
    }

    override fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}