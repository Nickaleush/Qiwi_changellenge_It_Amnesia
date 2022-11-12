package com.example.qiwi_changellenge_it_amnesia.ui.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.qiwi_changellenge_it_Amnesia.R
import com.example.qiwi_changellenge_it_amnesia.domain.models.ItemType
import com.example.qiwi_changellenge_it_amnesia.domain.models.ListItemProfile
import kotlinx.android.synthetic.main.recyclerview_item_profile.view.*

class ProfileAdapter(private val items: ArrayList<ListItemProfile> = ArrayList(), val fragment: ProfileFragment, context: Context) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(ItemType.values()[viewType].layoutRes, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (ItemType.values()[items[position].type.ordinal]) {

            ItemType.AnalyzePayments ->  {
                holder.itemView.iconMenu.setImageResource(R.drawable.payment_history_foreground)
                holder.itemView.textMenuItem.setText(R.string.PaymentHistory)
                holder.itemView.setBackgroundResource(R.drawable.bottom_line_edit_text)
                holder.itemView.setOnClickListener { fragment.openAnalyzePaymentFragment() }
            }

            ItemType.OpenShop ->  {
                holder.itemView.iconMenu.setImageResource(R.drawable.add_shop_foreground)
                holder.itemView.textMenuItem.setText(R.string.OpenShop)
                holder.itemView.setOnClickListener {
//                    fragment.openUsefulInfoFragment()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.ordinal
    }

    override fun getItemCount(): Int {
        return items.size
    }
}