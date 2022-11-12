package com.example.qiwi_changellenge_it_amnesia.domain.mainApi

import com.example.qiwi_changellenge_it_amnesia.domain.models.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainApi {

    @GET("api/client/account/payment")
    fun sendPaymentConfirmation (): Single<Unit>

    @POST("api/client/account/payment/confirm")
    fun confirmPayment(@Body code: Code): Single<PaymentToken>
}