package com.cloud.countries.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cloud.countries.databinding.ItemCountryBinding
import com.cloud.countries.getProgressDrawable
import com.cloud.countries.loadImage
import com.cloud.countries.model.Country

class CountryListAdapter(var countries:ArrayList<Country>) :  RecyclerView.Adapter<CountryListAdapter.CountryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemCountryBinding.inflate(inflater,parent,false)
        return CountryItemViewHolder(view)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holderCountryItem: CountryItemViewHolder, position: Int) {
        holderCountryItem.bind(countries[position])
    }

    fun updateCountries(newCountries: List<Country>) {
        var listSize = countries.size
        countries.clear()
        notifyItemRangeRemoved(0,listSize)
        countries.addAll(newCountries)
        listSize = countries.size
        notifyItemRangeInserted(0,listSize)
    }

    inner class CountryItemViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        private val progressDrawable = getProgressDrawable(binding.root.context)
        fun bind(item: Country) {
            with(binding) {
                name.text = item.countryName
                capital.text = item.capital
                imageView.loadImage(item.flagImagePath, progressDrawable)
            }
        }
    }

}