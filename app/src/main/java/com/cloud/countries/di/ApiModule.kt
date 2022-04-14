package com.cloud.countries.di

import com.cloud.countries.BuildConfig
import com.cloud.countries.model.CountriesApi
import com.cloud.countries.model.CountriesService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {
    private val BASE_URL = "https://raw.githubusercontent.com"

    @Provides
    fun provideCountriesApi():CountriesApi{
        //Http log
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        //Http Builder
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.interceptors().add(loggingInterceptor)
//        clientBuilder.addInterceptor { chain ->
//            val request = chain.request()
//            request.newBuilder().addHeader(AGENT_NAME, AGENT_VALUE).build()
//            chain.proceed(request)
//        }
//        clientBuilder.addInterceptor { chain ->
//            val request = chain.request()
//            request.newBuilder().addHeader(X_CLIENT_VERSION, APP_VERSION_NAME).build()
//            chain.proceed(request)
//        }
//        clientBuilder.addInterceptor { chain ->
//            val request = chain.request()
//            request.newBuilder().addHeader(UUID_NAME, deviceUUID).build()
//            chain.proceed(request)
//        }

        //Http client
        val client = clientBuilder
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }
    @Provides
    fun provideCountriesService()= CountriesService()
}