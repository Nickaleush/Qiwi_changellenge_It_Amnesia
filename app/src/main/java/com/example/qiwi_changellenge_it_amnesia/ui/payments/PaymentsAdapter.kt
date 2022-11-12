package com.example.qiwi_changellenge_it_amnesia.ui.payments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.di.modules.Payment
import kotlin.collections.ArrayList

class PaymentsAdapter(private val paymentList: ArrayList<Payment> ) : RecyclerView.Adapter<PaymentsAdapter.ViewHolder>() {

    private lateinit var mListener: OnClickListener

    private lateinit var age: String

    class ViewHolder(itemView: View, listener: OnClickListener) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.payment_item, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val v = holder.itemView
        val item = paymentList[position]
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    fun setOnClickRecyclerListener(listener: OnClickListener){
        mListener = listener
    }

    override fun getItemCount() = paymentList.size

}