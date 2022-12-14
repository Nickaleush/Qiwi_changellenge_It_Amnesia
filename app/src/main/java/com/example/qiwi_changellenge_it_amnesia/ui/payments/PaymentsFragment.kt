package com.example.qiwi_changellenge_it_amnesia.ui.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.App
import com.example.qiwi_changellenge_it_amnesia.domain.models.Payment
import com.example.qiwi_changellenge_it_amnesia.mvp.BaseFragment
import com.example.qiwi_changellenge_it_amnesia.ui.profile.ProfileFragment
import com.example.qiwi_changellenge_it_amnesia.ui.profile.ProfileFragment.Companion.SALES_OPENED
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.android.synthetic.main.payments_fragment.*
import java.util.ArrayList

class PaymentsFragment:  BaseFragment<PaymentsPresenterImpl>(), PaymentsView {

    private lateinit var skeleton: Skeleton

    override fun createComponent() {
        App.instance
            .getAppComponent()
            .createPaymentFragment()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.payments_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
        presenter.view = this
        toolbarPayments.setNavigationOnClickListener {
            onBackPressed()
        }
        skeleton = paymentsRecyclerView.applySkeleton(R.layout.payment_item)
        skeleton.maskCornerRadius = 30F
        skeleton.shimmerColor = requireActivity().getColor(R.color.mainColor)
        skeleton.showSkeleton()
        if (SALES_OPENED) presenter.getSalesList()
        else presenter.getPurchaseList()
    }

    override fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun showError(message: String?): Unit = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    override fun initRecyclerViewPurchaseList(purchaseList: ArrayList<Payment>) {
        if (purchaseList.isEmpty()) {
            emptyListTextView.visibility = View.VISIBLE
            emptyListImageView.visibility = View.VISIBLE
        } else {
            emptyListTextView.visibility = View.GONE
            emptyListImageView.visibility = View.GONE
        }
        paymentsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val adapter = PaymentsAdapter(purchaseList)
        paymentsRecyclerView.adapter = adapter
    }

    override fun hideSkeleton() {
        skeleton.showOriginal()
    }

}