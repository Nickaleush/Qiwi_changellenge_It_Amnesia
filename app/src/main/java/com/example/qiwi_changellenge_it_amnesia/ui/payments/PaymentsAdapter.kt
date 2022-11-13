package com.example.qiwi_changellenge_it_amnesia.ui.payments

import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.domain.models.Payment
import kotlinx.android.synthetic.main.payment_item.view.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class PaymentsAdapter(private val purchaseList: ArrayList<Payment> ) : RecyclerView.Adapter<PaymentsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.payment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val v = holder.itemView
        val item = purchaseList[position]
        v.paymentIdTextView.text = item.paymentId
        v.amountTextView.text = item.amount
        v.currencyTextview.text = item.currency
        v.shopNameTextview.text = item.shopName
        v.purchaserNameTextview.text = item.purchaserName
        val outputFormat= SimpleDateFormat("dd MMMM yyyy, HH:mm:ss")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val date= inputFormat.parse(item.createdDateTime)
        val formattedDate = outputFormat.format(date)
        v.paymentTimeTextview.text = formattedDate
    }

    override fun getItemCount() = purchaseList.size
}