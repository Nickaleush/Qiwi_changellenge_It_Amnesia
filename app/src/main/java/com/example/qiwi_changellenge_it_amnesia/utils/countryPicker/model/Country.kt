package com.example.qiwi_changellenge_it_amnesia.utils.countryPicker.model

import java.util.*

class Country : Comparator<Country?> {
    var countryName: String? = null
    var countryCode: String? = null
    var countryShortCode: String? = null

    constructor(
        countryName: String?,
        countryCode: String?,
        countryShortCode: String?
    ) {
        this.countryName = countryName
        this.countryCode = countryCode
        this.countryShortCode = countryShortCode
    }

    constructor() {}

    override fun compare(o1: Country?, o2: Country?): Int {
        return o1?.countryName!!.compareTo(o2?.countryName!!, ignoreCase = true)
    }
}
