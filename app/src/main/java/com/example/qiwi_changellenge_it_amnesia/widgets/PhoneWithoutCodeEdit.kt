package com.example.qiwi_changellenge_it_amnesia.widgets

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.*

open class PhoneWithoutCodeEdit: TextInputEditText {
    constructor(context: Context) : this(context, null) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        inputType = InputType.TYPE_CLASS_PHONE

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                var phoneString = editable.toString()
                val countryCode = Locale.getDefault().country
                var loginPhoneNumberCode = "+${PhoneNumberUtil.getInstance().getCountryCodeForRegion(countryCode)}"
                phoneString = loginPhoneNumberCode + phoneString

                val phoneUtils: PhoneNumberUtil = PhoneNumberUtil.getInstance()
                try {
                    val parsedNumber = phoneUtils.parse(phoneString, countryCode)
                    var phone = phoneUtils.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
                    phone = phone.removePrefix(loginPhoneNumberCode)

                    this@PhoneWithoutCodeEdit.removeTextChangedListener(this)
                    editable.replace(0, editable.toString().length, phone)
                    setSelection(editable.toString().length)
                    this@PhoneWithoutCodeEdit.addTextChangedListener(this)
                } catch (e: NumberParseException) {
                    // Do nothing
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }
        })

    }
}