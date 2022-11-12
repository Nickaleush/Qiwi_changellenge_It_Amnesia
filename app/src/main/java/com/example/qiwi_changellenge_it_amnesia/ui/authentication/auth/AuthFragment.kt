package com.example.qiwi_changellenge_it_amnesia.ui.authentication.auth

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.domain.models.Code
import com.example.qiwi_changellenge_it_amnesia.domain.models.UserToLogin
import com.example.qiwi_changellenge_it_amnesia.domain.models.UserToSignUp
import com.example.qiwi_changellenge_it_amnesia.domain.sharedPreferences.SharedPreferences
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import com.example.qiwi_changellenge_it_amnesia.ui.qr.QRFragment
import com.example.qiwi_changellenge_it_amnesia.utils.countryPicker.CCPicker
import com.example.qiwi_changellenge_it_amnesia.utils.countryPicker.adapter.CountryPickerAdapter
import com.example.qiwi_changellenge_it_amnesia.utils.countryPicker.model.Country
import com.example.qiwi_changellenge_it_amnesia.utils.countryPicker.utils.fadeIn
import com.example.qiwi_changellenge_it_amnesia.utils.countryPicker.utils.fadeOut
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jakewharton.rxbinding.widget.RxTextView
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import kotlinx.android.synthetic.main.auth_login.*
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.ContactsContract

import android.content.Intent
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.database.Cursor
import android.net.Uri
import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.ContactsContract.CommonDataKinds.Phone

class AuthFragment :  BaseFragment<AuthPresenterImpl>(), AuthView {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val REQUEST_CODE = 1

    private lateinit var sheetView: View

    private lateinit var mBottomSheetDialog: BottomSheetDialog

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createAuthFragment()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.auth_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
        presenter.view = this
        sheetView = requireActivity().layoutInflater.inflate(R.layout.confirmation_create_account, null)
        mBottomSheetDialog = BottomSheetDialog(requireActivity(), R.style.CustomBottomSheetDialogTheme)
            countryCodeTextView.setOnClickListener {
            CCPicker.showPicker(requireActivity(), object : CountryPickerAdapter.OnCountrySelectedListener {
                override fun onCountrySelected(country: Country?) {
                    val countryCode = country?.countryCode
                    countryCodeTextView.setText(countryCode)
                }
            })
        }

        numberFromContactsImageView.setOnClickListener {
            val uri = Uri.parse("content://contacts")
            val intent = Intent(Intent.ACTION_PICK, uri)
            intent.type = Phone.CONTENT_TYPE
            startActivityForResult(intent, REQUEST_CODE)
        }

        textViewRegister.setOnClickListener {
            editTextName.visibility = View.VISIBLE
            passwordEditText.visibility = View.VISIBLE
            val constraintSetSignUpForm = ConstraintSet()
            constraintSetSignUpForm.clone(auth_fragment_layout)
            changeConstraintsSignUp(constraintSetSignUpForm)
            TransitionManager.beginDelayedTransition(auth_fragment_layout)
            constraintSetSignUpForm.applyTo(auth_fragment_layout)
            passwordEditText.fadeIn( 400).mergeWith(editTextName.fadeIn( 400)).subscribe()
        }

        buttonDone.setOnClickListener {
            passwordEditText.visibility = View.VISIBLE
            val constraintSetLoginForm = ConstraintSet()
            constraintSetLoginForm.clone(auth_fragment_layout)
            changeConstraintsLogin(constraintSetLoginForm)
            TransitionManager.beginDelayedTransition(auth_fragment_layout)
            constraintSetLoginForm.applyTo(auth_fragment_layout)
            passwordEditText.fadeIn( 400).subscribe()
        }

        confirmCodeAccount = savedInstanceState?.getString("code").toString()
        val etTextConfirmCode  = sheetView.findViewById<EditText>(R.id.editTextConfirmAccountCode)
        val tvWrongCodeError  = sheetView.findViewById<TextView>(R.id.tv_wrongAccountCodeError)
        val tvRepeatSendCode  = sheetView.findViewById<TextView>(R.id.textViewRepeatSendAccountCode)
        if (etTextConfirmCode != null) {
            RxTextView.textChanges(etTextConfirmCode)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    confirmCodeAccount = it.toString()
                    if(tvWrongCodeError?.visibility == View.VISIBLE) {
                        tvWrongCodeError.visibility = View.GONE
                        tvRepeatSendCode?.visibility = View.VISIBLE
                        etTextConfirmCode.setBackgroundResource(R.drawable.bottom_line_edit_text)
                    }
                }, Throwable::printStackTrace)
        }
    }

    override fun onBackPressed() {
        requireActivity().finish()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    override fun navToUserProfileFragment() {
        mBottomSheetDialog.dismiss()
        Toast.makeText(requireContext(), getString(R.string.AuthenticationSuccess), Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_authFragment_to_QRFragment)
    }

    private fun changeConstraintsLogin(set: ConstraintSet) {
        set.clear(R.id.textViewReadOffer, ConstraintSet.TOP)
        set.clear(R.id.textViewReadOffer, ConstraintSet.LEFT)
        set.clear(R.id.textViewReadOffer, ConstraintSet.RIGHT)
        set.connect(R.id.textViewReadOffer, ConstraintSet.TOP, R.id.passwordEditText, ConstraintSet.BOTTOM, 36)
        buttonDone.setOnClickListener {
            val phoneNumber = phoneEditText.text.toString().replace(" ", "")
            val finalPhoneNumber = phoneNumber.replace("-", "")
            presenter.loginWithPhone(UserToLogin(passwordEditText.text.toString(),(countryCodeTextView.text.toString()+finalPhoneNumber)))
        }
    }

    private fun changeConstraintsSignUp(set: ConstraintSet) {
        set.clear(R.id.textViewReadOffer, ConstraintSet.TOP)
        set.clear(R.id.passwordEditText, ConstraintSet.TOP)
        set.connect(R.id.passwordEditText, ConstraintSet.TOP, R.id.editTextName, ConstraintSet.BOTTOM, 36)
        set.connect(R.id.textViewReadOffer, ConstraintSet.TOP, R.id.passwordEditText, ConstraintSet.BOTTOM, 36)

        buttonDone.setOnClickListener {
            val phoneNumber = phoneEditText.text.toString().replace(" ", "")
            val finalPhoneNumber = phoneNumber.replace("-", "")
            presenter.signUpWithPhone(UserToSignUp(editTextName.text.toString(), passwordEditText.text.toString(),(countryCodeTextView.text.toString()+finalPhoneNumber)))
        }

        textViewRegister.text = getString(R.string.Login)
        textViewRegister.setOnClickListener {
            textViewRegister.text = getString(R.string.NotRegisteredYet)
            val changeConstraintsLogin = ConstraintSet()
            changeConstraintsLogin.clone(auth_fragment_layout)
            changeConstraintsBackToLogin(changeConstraintsLogin)
            TransitionManager.beginDelayedTransition(auth_fragment_layout)
            changeConstraintsLogin.applyTo(auth_fragment_layout)
            editTextName.visibility = View.GONE
            editTextName.fadeOut(400).subscribe()
        }
    }

    private fun changeConstraintsBackToLogin(set: ConstraintSet) {
        set.clear(R.id.passwordEditText, ConstraintSet.TOP)
        set.connect(R.id.passwordEditText, ConstraintSet.TOP, R.id.phoneEditText, ConstraintSet.BOTTOM, 36)
        set.connect(R.id.textViewReadOffer, ConstraintSet.TOP, R.id.passwordEditText, ConstraintSet.BOTTOM, 36)

        buttonDone.setOnClickListener {
            val phoneNumber = phoneEditText.text.toString().replace("-","")
            val finalPhoneNumber = phoneNumber.replace(" ", "")
            presenter.loginWithPhone(UserToLogin(passwordEditText.text.toString(),(countryCodeTextView.text.toString()+finalPhoneNumber)))
        }

        textViewRegister.setOnClickListener {

            editTextName.visibility = View.VISIBLE
            passwordEditText.visibility = View.VISIBLE
            val constraintSetSignUpForm = ConstraintSet()
            constraintSetSignUpForm.clone(auth_fragment_layout)
            changeConstraintsSignUp(constraintSetSignUpForm)
            TransitionManager.beginDelayedTransition(auth_fragment_layout)
            constraintSetSignUpForm.applyTo(auth_fragment_layout)
            passwordEditText.fadeIn( 400).mergeWith(editTextName.fadeIn( 400)).subscribe()
        }
    }

    override fun showConfirmationDialog() {
            val buttonSendConfirmAccountCode  = sheetView.findViewById<Button>(R.id.buttonSendConfirmAccountCode)
            mBottomSheetDialog.setContentView(sheetView)
            mBottomSheetDialog.show()
            val mBehavior = BottomSheetBehavior.from(sheetView.parent as View)
            mBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            setupTimer()
            buttonSendConfirmAccountCode.setOnClickListener {
//                val etTextConfirmCode  = sheetView.findViewById<EditText>(R.id.editTextConfirmAccountCode)
                presenter.confirmAccount(Code(confirmCodeAccount))
                // запрос
            }
    }

    private fun setupTimer() {
        if(remainSeconds > 0) {
            startTime(remainSeconds)
        }
        else if(remainSeconds == 0) {
            showResendAction()
        }

    }

    private  fun startTime(time: Int){

        val tvWrongCodeError  = sheetView.findViewById<TextView>(R.id.tv_wrongCodeError)
        val tvRepeatSendCode  = sheetView.findViewById<TextView>(R.id.textViewRepeatSendCode)
        val tvResendCode  = sheetView.findViewById<TextView>(R.id.tv_resendCode)
        timerConfirmAccount?.cancel()
        timerConfirmAccount = Timer()
        timerConfirmAccount?.schedule(ResendCodeTask(time), 0, 1000)
        tvWrongCodeError?.visibility = View.GONE
        tvRepeatSendCode?.visibility = View.VISIBLE
        tvResendCode?.visibility = View.GONE
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
        val tvRepeatSendCode  = sheetView.findViewById<TextView>(R.id.textViewRepeatSendCode)
        requireActivity().runOnUiThread { tvRepeatSendCode?.text = String.format("%s %d:%02d", getString(R.string.resend_two_min), time / 60, time % 60)}
    }

    private fun cancelTimer() {
        remainSeconds = 0
        timerConfirmAccount?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelTimer()
        mBottomSheetDialog.cancel()
    }

    private fun showResendAction() {
        requireActivity().runOnUiThread {
            val etTextConfirmCode  = sheetView.findViewById<EditText>(R.id.editTextConfirmCode)
            val tvWrongCodeError  = sheetView.findViewById<TextView>(R.id.tv_wrongCodeError)
            val tvRepeatSendCode  = sheetView.findViewById<TextView>(R.id.textViewRepeatSendCode)
            val tvResendCode  = sheetView.findViewById<TextView>(R.id.tv_resendCode)
            etTextConfirmCode?.visibility = View.VISIBLE
            tvWrongCodeError?.visibility = View.GONE
            tvRepeatSendCode?.visibility = View.GONE
            tvResendCode?.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val uri: Uri = data?.data!!
                val projection = arrayOf(Phone.NUMBER, Phone.DISPLAY_NAME)
                val cursor: Cursor? = requireContext().contentResolver.query(uri, projection, null, null, null)
                cursor?.moveToFirst()
                val numberColumnIndex: Int? = cursor?.getColumnIndex(Phone.NUMBER)
                val number: String? = numberColumnIndex?.let {cursor.getString(it)}
                pickedPhoneNumber = if (number?.take(1).equals("+")) number?.takeLast(13).toString()
                else number?.takeLast(10).toString()
                phoneEditText.setText(pickedPhoneNumber)
                val nameColumnIndex: Int? = cursor?.getColumnIndex(Phone.DISPLAY_NAME)
            }
        }
    }

    companion object {
        private var remainSeconds = 120
        private var timerConfirmAccount: Timer? = null
        var confirmCodeAccount = ""
        var pickedPhoneNumber =""
    }
}