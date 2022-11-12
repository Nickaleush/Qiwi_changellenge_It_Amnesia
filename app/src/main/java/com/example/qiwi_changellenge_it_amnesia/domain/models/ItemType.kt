package com.example.qiwi_changellenge_it_amnesia.domain.models

import androidx.annotation.LayoutRes
import com.example.qiwi_changellenge_it_Amnesia.R

enum class ItemType(@LayoutRes val layoutRes: Int) {
    AnalyzePayments(R.layout.recyclerview_item_profile),
    OpenShop(R.layout.recyclerview_item_profile)
}