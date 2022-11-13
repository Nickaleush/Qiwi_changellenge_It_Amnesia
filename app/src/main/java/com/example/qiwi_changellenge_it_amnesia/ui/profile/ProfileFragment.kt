package com.example.qiwi_changellenge_it_amnesia.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.domain.models.ItemType
import com.example.qiwi_changellenge_it_amnesia.domain.models.ListItemProfile
import com.example.qiwi_changellenge_it_amnesia.domain.sharedPreferences.SharedPreferences
import com.example.qiwi_changellenge_it_amnesia.domain.models.ShopName
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import com.example.qiwi_changellenge_it_amnesia.ui.authentication.AuthFragment.Companion.pickedPhoneNumber
import com.example.qiwi_changellenge_it_amnesia.ui.qr.QRFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.profile_fragment.*
import javax.inject.Inject
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class ProfileFragment: BaseFragment<ProfilePresenterImpl>(), ProfileView {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var profileRecyclerViewItems: ArrayList<ListItemProfile> = ArrayList()

    private lateinit var menuAdapter: ProfileAdapter

    private lateinit var sheetView: View

    private lateinit var mBottomSheetDialog: BottomSheetDialog

    private lateinit var buttonSendCreatedShop: Button

    private lateinit var editTextCreatedShop: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
        presenter.view = this
        qiwiPhoneTextView.text = pickedPhoneNumber
        recyclerViewProfile.layoutManager = LinearLayoutManager(requireContext())
        menuAdapter = ProfileAdapter(profileRecyclerViewItems, this@ProfileFragment,requireContext())
        recyclerViewProfile.adapter = menuAdapter
        initializeData()
        buttonLogOut.setOnClickListener {
            sharedPreferences.accessToken = null
            findNavController().navigate(R.id.action_profileFragment_to_authFragment)
        }
    }

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createProfileFragment()
            .inject(this)
    }

    private fun initializeData() {
        profileRecyclerViewItems.clear()
        profileRecyclerViewItems.add(ListItemProfile(ItemType.AnalyzePayments))
        profileRecyclerViewItems.add(ListItemProfile(ItemType.OpenShop))
        profileRecyclerViewItems.add(ListItemProfile(ItemType.OpenReadQRFragment))
    }

    fun openAnalyzePaymentFragment() {
        findNavController().navigate(R.id.action_profileFragment_to_paymentsFragment)
    }

    fun openCreateShopDialog() {
        sheetView = requireActivity().layoutInflater.inflate(R.layout.create_shop, null)
        mBottomSheetDialog = BottomSheetDialog(requireActivity(), R.style.CustomBottomSheetDialogTheme)

        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()

        val mBehavior = BottomSheetBehavior.from(sheetView.parent as View)
        mBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        buttonSendCreatedShop  = sheetView.findViewById(R.id.buttonSendCreatedShop)
        editTextCreatedShop  = sheetView.findViewById(R.id.editTextCreatedShop)

        RxTextView.textChanges(editTextCreatedShop)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                shopName = it.toString()
            }, Throwable::printStackTrace)
        buttonSendCreatedShop.setOnClickListener {
            presenter.createShopAccount(ShopName(shopName))
        }
    }
    fun openReadQRFragment(){
        findNavController().navigate(R.id.action_profileFragment_to_readQRFragment)
    }
    override fun closeBottomSheetDialog(){
        mBottomSheetDialog.dismiss()
    }
    override fun onBackPressed() {
        requireActivity().finish()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    companion object{
        var shopName = ""
    }
}