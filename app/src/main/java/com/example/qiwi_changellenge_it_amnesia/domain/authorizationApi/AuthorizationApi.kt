package com.example.qiwi_changellenge_it_amnesia.domain.authorizationApi

import com.example.qiwi_changellenge_it_amnesia.domain.models.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationApi {

    @POST("api/...")
    fun signUpWithPhone (@Body user: UserToSignUp): Single<Token>

    @POST("api/...")
    fun loginWithPhone(@Body user: UserToLogin): Single<Token>

}