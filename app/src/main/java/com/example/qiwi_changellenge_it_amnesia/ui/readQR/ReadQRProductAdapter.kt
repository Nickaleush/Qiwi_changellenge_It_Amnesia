package com.example.qiwi_changellenge_it_amnesia.ui.readQR

import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.domain.models.Product
import kotlinx.android.synthetic.main.item_product.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ReadQRProductAdapter(private val productList: MutableList<Product>, private val iconList: IntArray) : RecyclerView.Adapter<ReadQRProductAdapter.ViewHolder>() {

    private lateinit var mListener: OnClickListener

    class ViewHolder(itemView: View, listener: OnClickListener) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.productItemCardView.setOnClickListener {
                listener.onClick(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val v = holder.itemView
        val item = productList[position]
        v.productName.text = item.amount.toString()
        v.productImage.setImageResource(iconList[position])
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    fun setOnClickRecyclerListener(listener: OnClickListener){
        mListener = listener
    }

    override fun getItemCount() = productList.size
}
