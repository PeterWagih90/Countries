package com.cloud.countries.model

import io.reactivex.Single
import retrofit2.http.GET

interface CountriesApi {
    @GET("CatalinStefan/countries/master/countriesV2.json")
    fun getCountries(): Single<List<Country>>
}