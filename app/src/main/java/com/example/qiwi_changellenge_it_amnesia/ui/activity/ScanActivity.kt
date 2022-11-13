package com.example.qiwi_changellenge_it_amnesia.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.qiwi_changellenge_it_amnesia.ui.readQR.ReadQRFragment.Companion.paymentToken
import com.example.qiwi_changellenge_it_amnesia.ui.readQR.ReadQRFragment.Companion.scanQR
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this)
        setContentView(mScannerView)
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }

    override fun handleResult(rawResult: Result) {
        scanQR = true
        paymentToken =rawResult.text
        onBackPressed()

    }
}