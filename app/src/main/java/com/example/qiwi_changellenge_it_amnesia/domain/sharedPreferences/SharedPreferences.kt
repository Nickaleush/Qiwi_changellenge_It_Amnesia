package com.example.qiwi_changellenge_it_amnesia.domain.sharedPreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    var accessToken: String?
        get() = sharedPreferences.getString(ACCESS_TOKEN, null)
        set(value) = sharedPreferences.edit {
            putString(ACCESS_TOKEN, value)
            apply()
            commit()
        }

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }
}