package com.cloud.countries.view

import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloud.countries.databinding.ActivityMainBinding
import com.cloud.countries.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ListViewModel
    private val countryListAdapter = CountryListAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        binding.apply {
            recyclerView.apply {
                this.layoutManager = LinearLayoutManager(context)
                this.adapter = countryListAdapter
            }
            swipe.setOnRefreshListener {
                swipe.isRefreshing = false
                viewModel.refresh()
            }

        }

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.countries.observe(this, Observer {
            it?.let {
                binding.recyclerView.isVisible = true
                countryListAdapter.updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(this, Observer {
            binding.tvMessage.isVisible = it
        })

        viewModel.loading.observe(this, Observer {
            binding.progressBar.isVisible = it
            if(it) {
                binding.tvMessage.isVisible = !it
                binding.recyclerView.isVisible = !it
            }

        })
    }

}