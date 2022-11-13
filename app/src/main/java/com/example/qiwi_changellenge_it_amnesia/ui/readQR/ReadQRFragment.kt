package com.example.qiwi_changellenge_it_amnesia.ui.readQR

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.domain.models.PaymentBody
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import com.example.qiwi_changellenge_it_amnesia.ui.activity.ScanActivity
import kotlinx.android.synthetic.main.read_qr_fragment.*


class ReadQRFragment: BaseFragment<ReadQRPresenterImpl>(), ReadQRView {

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createReadQRFragment()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.read_qr_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
        presenter.view = this
        checkPermissionGranted()
        buttonReadQR.setOnClickListener {
            val intent = Intent(requireActivity(), ScanActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkPermissionGranted(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED){
            val permissions = arrayOf(Manifest.permission.CAMERA)
            ActivityCompat.requestPermissions(requireActivity(), permissions,0)
        }
    }

    override fun onResume() {
        super.onResume()
        if(scanQR && paymentToken.length>20){
            presenter.sendTransaction(PaymentBody("2000", paymentToken))
        }
    }

    override fun successPay(){
        Toast.makeText(activity, "Охуенно" ,Toast.LENGTH_LONG).show()
    }
    override fun onBackPressed() {
        requireActivity().finish()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    companion object{
        var scanQR = false
        var paymentToken = ""
    }
}