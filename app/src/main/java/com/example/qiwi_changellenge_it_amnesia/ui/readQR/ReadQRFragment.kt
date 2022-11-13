package com.example.qiwi_changellenge_it_amnesia.ui.readQR

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import android.app.Activity.RESULT_OK
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.domain.models.PaymentBody
import com.example.qiwi_changellenge_it_amnesia.utils.Device
import com.google.zxing.integration.android.IntentIntegrator

class ReadQRFragment: BaseFragment<ReadQRPresenterImpl>(), ReadQRView {

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createReadQRFragment()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pickQRCode()
    }

    private fun pickQRCode() {
        if (Device.isCameraAvailable(requireContext())) {
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            integrator.setPrompt(getString(R.string.SCANBARCODE))
            integrator.setOrientationLocked(false)
            integrator.initiateScan()
        } else {
            Toast.makeText(activity, R.string.NOCAMERA,
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK) {
            val paymentToken = data?.getStringExtra("SCAN_RESULT")
            if (paymentToken != null) {
               presenter.sendTransaction(PaymentBody("2000", paymentToken))
            }
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents != null) {
                    val paymentInfo = result.contents
                }
                else {
                    Toast.makeText(activity, R.string.invalid_QR_code ,Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    override fun successPay() {
        Toast.makeText(activity, "Охуенно" ,Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    companion object{
        var paymentToken = ""
    }
}