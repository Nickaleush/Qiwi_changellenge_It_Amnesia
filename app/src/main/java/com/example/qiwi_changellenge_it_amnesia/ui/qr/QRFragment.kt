package com.example.qiwi_changellenge_it_amnesia.ui.qr

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.domain.models.Code
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.alert_qr_code.*
import kotlinx.android.synthetic.main.confirmation_create_qr_code.view.*
import kotlinx.android.synthetic.main.qr_fragment.*
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit


class QRFragment: BaseFragment<QRPresenterImpl>(), QRFragmentView  {

    private var bitmap: Bitmap? = null

    private lateinit var alertDialogBuilder: AlertDialog.Builder

    private lateinit var sheetView: View

    private lateinit var mBottomSheetDialog: BottomSheetDialog

    private lateinit var tvWrongCodeError: TextView

    private lateinit var tvRepeatSendCode: TextView

    private lateinit var tvResendCode: TextView

    private lateinit var etTextConfirmCode: EditText

    private lateinit var btnSendConfirmCode: Button

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createQRFragment()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.qr_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
        presenter.view = this
        btn_createQR.setOnClickListener {
            try {
                presenter.sendPaymentConfirmation()
                bitmap = textToImageEncode("Token")
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }

        confirmCode = savedInstanceState?.getString("code").toString()

    }

    private fun setupTimer() {
        if(remainSec > 0) {
            startTime(remainSec)
        }
        else if(remainSec == 0){
            showResendAction()
        }

    }

    private  fun startTime(time: Int){
        timer?.cancel()
        timer = Timer()
        timer?.schedule(ResendCodeTask(time), 0, 1000)
        tvWrongCodeError.visibility = View.GONE
        tvRepeatSendCode.visibility = View.VISIBLE
        tvResendCode.visibility = View.GONE
    }

    inner class ResendCodeTask(private var timeOutSec: Int): TimerTask() {
        override fun run() {
            if(timeOutSec <= 0) {
                showResendAction()
                cancelTimer()
                cancel()
                return
            }
            updateTimeText(timeOutSec)
            timeOutSec--
        }
    }

    private fun updateTimeText(time: Int){
        requireActivity().runOnUiThread { tvRepeatSendCode.text = String.format("%s %d:%02d", getString(R.string.resend_two_min), time / 60, time % 60)}
    }

    private fun showResendAction() {
        requireActivity().runOnUiThread {
            etTextConfirmCode.visibility = View.VISIBLE
            tvWrongCodeError.visibility = View.GONE
            tvRepeatSendCode.visibility = View.GONE
            tvResendCode.visibility = View.VISIBLE
        }
    }

    private fun cancelTimer() {
        remainSec = 0
        timer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTimer()
    }

    override fun startConfirmationCreateQRCode() {
        sheetView = requireActivity().layoutInflater.inflate(R.layout.confirmation_create_qr_code, null)
        mBottomSheetDialog = BottomSheetDialog(requireActivity(), R.style.CustomBottomSheetDialogTheme)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()

        val mBehavior = BottomSheetBehavior.from(sheetView.parent as View)
        mBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        etTextConfirmCode  = sheetView.findViewById(R.id.editTextConfirmCode)
        tvWrongCodeError  = sheetView.findViewById(R.id.tv_wrongCodeError)
        tvRepeatSendCode  = sheetView.findViewById(R.id.textViewRepeatSendCode)
        tvResendCode  = sheetView.findViewById(R.id.tv_resendCode)
        btnSendConfirmCode = sheetView.findViewById(R.id.buttonSendConfirmCode)

        setupTimer()

        btnSendConfirmCode.setOnClickListener {
            presenter.confirmPayment(Code(confirmCode))
        }
        RxTextView.textChanges(etTextConfirmCode)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                confirmCode = it.toString()
                if(tvWrongCodeError?.visibility == View.VISIBLE) {
                    tvWrongCodeError.visibility = View.GONE
                    tvRepeatSendCode?.visibility = View.VISIBLE
                    etTextConfirmCode.setBackgroundResource(R.drawable.bottom_line_edit_text)
                }
            }, Throwable::printStackTrace)
    }

    override fun closeConfirmAndDrawQR(){
        mBottomSheetDialog.dismiss()
        drawQRCode()
    }
    private fun drawQRCode() {
        alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setPositiveButton("OK"){ _, _ ->
                return@setPositiveButton
            }
        val inflater = layoutInflater

        val dialogLayout = inflater.inflate(R.layout.alert_qr_code, null)
        val imageQRCode  = dialogLayout.findViewById<ImageView>(R.id.image_qrCode)
        alertDialogBuilder.setView(dialogLayout)
        alertDialogBuilder.show()
        val overlay = BitmapFactory.decodeResource(resources, R.drawable.small_icon)
        imageQRCode!!.setImageBitmap(bitmap?.let { mergeBitmaps(overlay, it) })

    }

    @Throws(WriterException::class)
    private fun textToImageEncode(Value: String): Bitmap? {
        val bitMatrix: BitMatrix
        try {
            bitMatrix = MultiFormatWriter().encode(
                Value,
                BarcodeFormat.QR_CODE,
                QRcodeWidth, QRcodeWidth, null
            )
        } catch (Illegalargumentexception: IllegalArgumentException) {
            return null
        }

        val bitMatrixWidth = bitMatrix.width
        val bitMatrixHeight = bitMatrix.height
        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {
                pixels[offset + x] = if (bitMatrix.get(x, y))
                    resources.getColor(R.color.black, null)
                else
                    resources.getColor(R.color.white, null)
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_8888)

        bitmap.setPixels(pixels, 0, bitMatrixWidth, 0, 0, bitMatrixWidth, bitMatrixHeight)
        //getting the logo
        //getting the logo

        //setting bitmap to image view
        //setting bitmap to image view
        return bitmap
    }

    private fun mergeBitmaps(overlay: Bitmap, bitmap: Bitmap): Bitmap? {
        val height = bitmap.height
        val width = bitmap.width
        val combined = Bitmap.createBitmap(width, height, bitmap.config)
        val canvas = Canvas(combined)
        val canvasWidth: Int = canvas.width
        val canvasHeight: Int = canvas.height
        canvas.drawBitmap(bitmap, Matrix(), null)
        val centreX  = ((canvasWidth - overlay.width) / 2).toFloat()
        val centreY = ((canvasHeight - overlay.height) / 2).toFloat()
        canvas.drawBitmap(overlay, centreX, centreY, null)
        return combined
    }
    private fun showInvalidCodeError() {
        etTextConfirmCode.background = ResourcesCompat.getDrawable(resources,R.drawable.bottom_line_edit_text_red, null)
        tvWrongCodeError.visibility = View.VISIBLE
        tvRepeatSendCode.visibility = View.GONE
        tvResendCode.visibility = View.GONE
    }
    override fun onBackPressed() {
        requireActivity().finish()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    override fun showUserCodeError() {
        showInvalidCodeError()
    }

    companion object{
        private var remainSec = 120
        private var timer: Timer? = null
        var confirmCode = ""
        const val QRcodeWidth = 1000
    }
}