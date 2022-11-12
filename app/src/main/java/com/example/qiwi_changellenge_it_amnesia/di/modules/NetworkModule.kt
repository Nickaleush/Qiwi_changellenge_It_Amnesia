package com.example.qiwi_changellenge_it_amnesia.di.modules

import com.example.qiwi_changellenge_it_Amnesia.BuildConfig
import com.example.qiwi_changellenge_it_amnesia.domain.ServiceGenerator
import com.example.qiwi_changellenge_it_amnesia.domain.authorizationApi.AuthorizationApi
import com.example.qiwi_changellenge_it_amnesia.domain.interceptors.AccessTokenInterceptor
import com.example.qiwi_changellenge_it_amnesia.domain.interceptors.NoneAuthInterceptor
import com.example.qiwi_changellenge_it_amnesia.domain.mainApi.MainApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Singleton
    @Provides
    fun provideNoneAuthApi(gson: Gson, noneAuthInterceptor: NoneAuthInterceptor, loggingInterceptor: HttpLoggingInterceptor): AuthorizationApi {
        val interceptors = arrayOf(noneAuthInterceptor, loggingInterceptor)
        return ServiceGenerator.generate(BuildConfig.BASE_URL, AuthorizationApi::class.java, gson, null, interceptors)
    }

    @Singleton
    @Provides
    fun provideAuthApi(gson: Gson, authInterceptor: AccessTokenInterceptor, loggingInterceptor: HttpLoggingInterceptor): MainApi {
        val interceptors = arrayOf(authInterceptor, loggingInterceptor)
        return ServiceGenerator.generate(BuildConfig.BASE_URL, MainApi::class.java, gson, null,  interceptors)
    }

}