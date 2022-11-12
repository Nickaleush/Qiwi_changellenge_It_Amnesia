package com.example.qiwi_changellenge_it_amnesia.widgets.countryPicker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.widgets.countryPicker.model.Country

class CountryPickerAdapter(private var countries:ArrayList<Country>, private var onCountrySelectedListener: OnCountrySelectedListener) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val VIEW_TYPE_RESULT = 0
    private val VIEW_TYPE_NO_RESULT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_RESULT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_country_picker, parent, false)
            MyViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_view_no_result_found, parent, false)
            NoResultViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return if (countries.size != 0) countries.size else 1
    }

    fun setCountries(countries: ArrayList<Country>) {
        this.countries = countries
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (countries.size != 0) {
           VIEW_TYPE_RESULT
        } else {
            VIEW_TYPE_NO_RESULT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            (holder as MyViewHolder).bind(position)
        } else {
            (holder as NoResultViewHolder).bind(position)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCountryName: TextView = itemView.findViewById(R.id.tvCountryName)
        private val tvCountryCode: TextView = itemView.findViewById(R.id.tvCountryCode)
        private val countryFlagTV: TextView = itemView.findViewById(R.id.countryFlagTV)

        fun bind(position: Int){
            val flagOffset = 0x1F1E6
            val asciiOffset = 0x41
            val country: String = countries[position].countryShortCode?:""
            val firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset
            val secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset

            val flag = (String(Character.toChars(firstChar))
                    + String(Character.toChars(secondChar)))

            countryFlagTV.text =flag
            tvCountryName.text =  countries[position].countryName?:""
            tvCountryCode.text =  countries[position].countryCode?:""

            itemView.setOnClickListener {
                if(onCountrySelectedListener !=  null){
                    onCountrySelectedListener.onCountrySelected(countries[position])
                }
            }
        }
    }

    inner class NoResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNoResultFound: TextView = itemView.findViewById(R.id.tvNoResultFound)
        fun bind(position: Int){
            tvNoResultFound.text = "No Countries Found"
        }
    }



    interface OnCountrySelectedListener {
        fun onCountrySelected(country: Country?)
    }
}