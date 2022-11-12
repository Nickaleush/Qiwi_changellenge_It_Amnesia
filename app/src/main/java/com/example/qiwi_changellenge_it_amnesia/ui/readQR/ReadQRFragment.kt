package com.example.qiwi_changellenge_it_amnesia.ui.readQR

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment

class ReadQRFragment: BaseFragment<ReadQRPresenterImpl>(), ReadQRView {

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createReadQRFragment()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onBackPressed() {
        requireActivity().finish()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

}