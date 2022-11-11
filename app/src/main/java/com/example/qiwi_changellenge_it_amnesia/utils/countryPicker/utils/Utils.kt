package com.example.qiwi_changellenge_it_amnesia.utils.countryPicker.utils

import android.content.Context
import com.example.qiwi_changellenge_it_amnesia.utils.countryPicker.model.Country
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList

object Utils {

    fun loadJSONFromAsset(context: Context):ArrayList<Country> {
        val countryList: ArrayList<Country> = ArrayList()
        val json: String?
        json = try {
            val `is` = context.assets.open("countries_list")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            return ArrayList()
        }

        try {
            val obj = JSONObject(json)
            val m_jArry = obj.getJSONArray("countries")
            for (i in 0 until m_jArry.length()) {
                val jo_inside = m_jArry.getJSONObject(i)
                val country = Country()
                val loc = Locale("", jo_inside.getString("countryShortCode"))
                country.countryName=loc.displayCountry
                country.countryCode= "+" + jo_inside.getString("countryCode").replace("-", "")
                country.countryShortCode = jo_inside.getString("countryShortCode")
                countryList.add(country)
            }
            Collections.sort(countryList, Country())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return countryList
    }
}