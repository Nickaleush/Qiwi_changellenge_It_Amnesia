package com.example.qiwi_changellenge_it_amnesia.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.example.qiwi_changellenge_it_Amnesia.R

@SuppressLint("InflateParams")
class CustomProgressDialog(context: Context) {

    private var dialog: CustomDialog
    private var cpCardView: CardView
    private var progressBar: ProgressBar

    fun start() {
        dialog.show()
    }

    fun stop() {
        dialog.dismiss()
    }

    init {
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.progress_dialog_view, null)
        cpCardView = view.findViewById(R.id.cp_cardview)
        progressBar = view.findViewById(R.id.cp_pbar)

        cpCardView.setCardBackgroundColor(Color.parseColor("#70000000"))

        setColorFilter(
            progressBar.indeterminateDrawable,
            ResourcesCompat.getColor(context.resources, R.color.mainColor, null)
        )

        dialog = CustomDialog(context)
        dialog.setContentView(view)
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context, R.style.CustomProgressBarTheme) {
        init {
            window?.decorView?.rootView?.setBackgroundResource(R.color.dialogBackground)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}