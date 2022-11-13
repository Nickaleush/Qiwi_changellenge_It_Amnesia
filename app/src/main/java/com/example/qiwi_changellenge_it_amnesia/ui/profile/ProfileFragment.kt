package com.example.qiwi_changellenge_it_amnesia.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.domain.models.ItemType
import com.example.qiwi_changellenge_it_amnesia.domain.models.ListItemProfile
import com.example.qiwi_changellenge_it_amnesia.domain.sharedPreferences.SharedPreferences
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import com.example.qiwi_changellenge_it_amnesia.ui.authentication.AuthFragment.Companion.pickedPhoneNumber
import kotlinx.android.synthetic.main.profile_fragment.*
import javax.inject.Inject

class ProfileFragment: BaseFragment<ProfilePresenterImpl>(), ProfileView {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var profileRecyclerViewItems: ArrayList<ListItemProfile> = ArrayList()

    private lateinit var menuAdapter: ProfileAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }

    fun openAnalyzePaymentFragment() {
        findNavController().navigate(R.id.action_profileFragment_to_paymentsFragment)
    }

    fun openMagazineDialog() {

    }

    override fun onBackPressed() {
        requireActivity().finish()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}