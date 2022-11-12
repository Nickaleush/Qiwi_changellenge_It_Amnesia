package com.example.qiwi_changellenge_it_amnesia.ui.authentication.auth

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.domain.sharedPreferences.SharedPreferences
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import com.example.qiwi_changellenge_it_amnesia.utils.countryPicker.CCPicker
import com.example.qiwi_changellenge_it_amnesia.utils.countryPicker.adapter.CountryPickerAdapter
import com.example.qiwi_changellenge_it_amnesia.utils.countryPicker.model.Country
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import kotlinx.android.synthetic.main.auth_login.*
import javax.inject.Inject

class AuthFragment :  BaseFragment<AuthPresenterImpl>(), AuthView {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

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
            countryCodeTextView.setOnClickListener {
            CCPicker.showPicker(requireActivity(), object : CountryPickerAdapter.OnCountrySelectedListener {
                override fun onCountrySelected(country: Country?) {
                    val countryCode = country?.countryCode
                    countryCodeTextView.setText(countryCode)
                }
            })
        }

        buttonDone.setOnClickListener {
            val constraintSetShortForm = ConstraintSet()
            constraintSetShortForm.clone(auth_fragment_layout)
            changeConstraintsBack(constraintSetShortForm)
            TransitionManager.beginDelayedTransition(auth_fragment_layout)
            constraintSetShortForm.applyTo(auth_fragment_layout)
            passwordEditText.fadeIn( 300)
        }
    }

    override fun onBackPressed() {
        requireActivity().finish()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    override fun navToUserProfileFragment() {}

    private fun View.fadeIn(duration: Long): Completable {
        val animationSubject = CompletableSubject.create()
        return animationSubject.doOnSubscribe {
            ViewCompat.animate(this)
                .setDuration(duration)
                .alpha(1f)
                .withEndAction {
                    animationSubject.onComplete()
                }
        }
    }

    private fun changeConstraintsBack(set: ConstraintSet) {
        set.clear(R.id.textViewReadOffer, ConstraintSet.TOP)
        set.clear(R.id.textViewReadOffer, ConstraintSet.LEFT)
        set.clear(R.id.textViewReadOffer, ConstraintSet.RIGHT)
        set.connect(R.id.textViewReadOffer, ConstraintSet.TOP, R.id.passwordEditText, ConstraintSet.BOTTOM, 32)
    }

    override fun showProgressBar() {
        //progressDialog.start()
    }

    companion object {
//        const val phoneAuthKey = "USER_NUMBER_KEY"
//        private var LOGIN_WITH_PHONE = false
//        var NAV_BACK_PRESSED = false
//        private var savedAuthPhoneCode: String? = "+7"
//        private var savedAuthPhone: String? = null
//        var OPEN_AUTH_FRAGMENT = false
//        var PHONE_RECOVERY = false
    }
}