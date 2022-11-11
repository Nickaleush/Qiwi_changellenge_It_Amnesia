package com.example.qiwi_changellenge_it_amnesia.di.modules

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

//    @Singleton
//    @Provides
//    fun provideAuthApi(gson: Gson, authInterceptor: AccessTokenInterceptor, loggingInterceptor: HttpLoggingInterceptor, tokenAuthenticator: AccessTokenAuthenticator, retryOnUnauthorizedInterceptor: RetryOnUnauthorizedInterceptor): PetApi {
//        val interceptors = arrayOf(authInterceptor, loggingInterceptor, retryOnUnauthorizedInterceptor)
//        return ServiceGenerator.generate(BuildConfig.BASE_URL, PetApi::class.java, gson, tokenAuthenticator,  interceptors)
//    }

}