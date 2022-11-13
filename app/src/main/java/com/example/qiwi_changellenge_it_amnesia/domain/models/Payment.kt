package com.example.qiwi_changellenge_it_amnesia.domain.models

import java.time.OffsetDateTime

data class Payment(
    val id: Long,
    val paymentId: String,
    val billId:String,
    val createdDateTime: OffsetDateTime,
    val status: String,
    val amount: String,
    val currency: String,
    val shopName: String,
    val purchaserName: String
    )