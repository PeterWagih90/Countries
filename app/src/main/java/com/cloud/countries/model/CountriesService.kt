package com.cloud.countries.model

import com.cloud.countries.BuildConfig
import com.cloud.countries.di.DaggerApiComponent
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CountriesService(){
    @Inject
    lateinit var api:CountriesApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }

    companion object {
        private var apiClient: CountriesService? = null

        fun getInstance(): CountriesService =
            apiClient ?: synchronized(this) {
                apiClient ?: CountriesService().also {
                    apiClient = it
                }
            }
    }


}