package com.example.qiwi_changellenge_it_amnesia.domain.authorizationApi

import com.example.qiwi_changellenge_it_amnesia.domain.models.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationApi {

    @POST("api/auth/sign-up")
    fun signUpWithPhone (@Body user: UserToSignUp): Single<AccessToken>

    @POST("api/auth/log-in")
    fun loginWithPhone(@Body user: UserToLogin): Single<AccessToken>

}