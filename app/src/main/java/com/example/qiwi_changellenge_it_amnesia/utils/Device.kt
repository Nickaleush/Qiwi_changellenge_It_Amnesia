package com.example.qiwi_changellenge_it_amnesia.utils

import android.content.Context
import android.content.pm.PackageManager

object Device {
    @JvmStatic
    fun isCameraAvailable(context: Context): Boolean {
        val pm = context.packageManager
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }
}