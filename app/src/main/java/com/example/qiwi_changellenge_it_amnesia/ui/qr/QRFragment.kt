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
import android.widget.ImageView
import android.widget.Toast
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import kotlinx.android.synthetic.main.alert_qr_code.*
import kotlinx.android.synthetic.main.qr_fragment.*


class QRFragment: BaseFragment<QRPresenterImpl>(), QRFragmentView  {

    private var bitmap: Bitmap? = null

    private lateinit var alertDialogBuilder: AlertDialog.Builder

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
                bitmap = textToImageEncode("Token")
                drawQRCode(bitmap)
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }
    }

    private fun drawQRCode(bitmap: Bitmap?) {
        alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setPositiveButton("OK"){ _, _ ->
                return@setPositiveButton
            }
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.alert_qr_code, null)
        val imageQRCode  = dialogLayout.findViewById<ImageView>(R.id.image_qrCode)
        alertDialogBuilder.setView(dialogLayout)
        val overlay = BitmapFactory.decodeResource(resources, R.drawable.small_icon)
        imageQRCode!!.setImageBitmap(bitmap?.let { mergeBitmaps(overlay, it) })
        alertDialogBuilder.show()
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
        val canvasWidth: Int = canvas.getWidth()
        val canvasHeight: Int = canvas.getHeight()
        canvas.drawBitmap(bitmap, Matrix(), null)
        val centreX  = ((canvasWidth - overlay.width) / 2).toFloat()
        val centreY = ((canvasHeight - overlay.height) / 2).toFloat()
        canvas.drawBitmap(overlay, centreX, centreY, null)
        return combined
    }
    override fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    
    companion object{
        const val QRcodeWidth = 1000
    }
}