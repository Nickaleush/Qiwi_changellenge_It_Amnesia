package com.example.qiwi_changellenge_it_amnesia.widgets.countryPicker

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.widgets.countryPicker.adapter.CountryPickerAdapter
import com.example.qiwi_changellenge_it_amnesia.widgets.countryPicker.model.Country
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.example.qiwi_changellenge_it_amnesia.widgets.countryPicker.utils.Utils

class CCPicker {

    companion object {

        fun showPicker(
            activity: Activity,
            onCountrySelectedListener: CountryPickerAdapter.OnCountrySelectedListener
        ){
            val sheetView = activity.layoutInflater.inflate(R.layout.country_code_picker, null)
            val mBottomSheetDialog = BottomSheetDialog(activity, R.style.CustomBottomSheetDialogTheme)
            mBottomSheetDialog.setContentView(sheetView)
            mBottomSheetDialog.show()
            val mBehavior = BottomSheetBehavior.from(sheetView.parent as View)
            mBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            sheetView.findViewById<RecyclerView>(R.id.rvCountryPicker).layoutManager = LinearLayoutManager(activity)

            val countries = Utils.loadJSONFromAsset(activity)

            val adapter = CountryPickerAdapter(countries, object : CountryPickerAdapter.OnCountrySelectedListener {
                override fun onCountrySelected(country: Country?) {
                    onCountrySelectedListener.onCountrySelected(country)
                    mBottomSheetDialog.dismiss()
                }
            })

            sheetView.findViewById<RecyclerView>(R.id.rvCountryPicker).adapter = adapter

            sheetView.findViewById<ImageView>(R.id.ivClose).setOnClickListener {
                mBottomSheetDialog.dismiss()
            }
        }
    }
}