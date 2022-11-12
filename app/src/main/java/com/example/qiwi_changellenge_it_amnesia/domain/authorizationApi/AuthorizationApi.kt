package com.example.qiwi_changellenge_it_amnesia.domain.authorizationApi

import com.example.qiwi_changellenge_it_amnesia.domain.models.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationApi {

    @POST("api/...")
    fun signUpBuyerWithPhone (@Body userBuyer: BuyerToSignUp): Single<Token>

    @POST("api/...")
    fun signUpSellerWithPhone (@Body userSeller: SellerToSignUp): Single<Token>

    @POST("api/...")
    fun loginBuyerWithPhone(@Body userBuyer: BuyerToLogin): Single<Token>

    @POST("api/...")
    fun loginSellerWithPhone(@Body userSeller: SellerToLogin): Single<Token>

}