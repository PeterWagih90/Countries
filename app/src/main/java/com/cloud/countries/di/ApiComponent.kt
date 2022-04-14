package com.cloud.countries.di

import androidx.lifecycle.ViewModel
import com.cloud.countries.model.CountriesService
import com.cloud.countries.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service:CountriesService)
    fun inject(viewModel: ViewModel)
}