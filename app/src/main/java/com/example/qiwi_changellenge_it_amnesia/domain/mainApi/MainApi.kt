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

    @POST("api/confirmation/client")
    fun confirmAccount(@Body code: Code): Single<AccessToken>

    @GET("api/payments/purchases")
    fun getPurchaseList(): Single<ArrayList<Payment>>

    @GET("api/payments/sales")
    fun getSalesList(): Single<ArrayList<Payment>>

    @POST("api/shop/account")
    fun sendTransaction(@Body paymentBody: PaymentBody): Single<Unit>

    @POST("api/client/account/shop")
    fun createShopAccount(@Body shopName: ShopName): Single<AccessToken>
}