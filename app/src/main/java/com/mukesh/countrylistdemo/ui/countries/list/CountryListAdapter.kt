package com.mukesh.countrylistdemo.ui.countries.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mukesh.countrylistdemo.data.model.Country
import com.mukesh.countrylistdemo.databinding.RowCountryBinding

/**
Created by Mukesh on 27/02/23
 Country list adapter
 **/

class CountryListAdapter: Adapter<CountryListAdapter.CountryViewHolder>() {
    private val countryList = mutableListOf<Country>()
    var onItemClick: ((Country) ->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflator= LayoutInflater.from(parent.context)
        val binding = RowCountryBinding.inflate(inflator,parent,false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.setData(countryList[position])
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun setData(list: List<Country>){
        countryList.clear()
        countryList.addAll(list)
        notifyItemRangeChanged(0,list.size)
    }


    inner class CountryViewHolder(private val binding: RowCountryBinding): ViewHolder(binding.root) {
        fun setData(country: Country) {
            binding.name.text = country.countryName
            binding.root.setOnClickListener {
                onItemClick?.invoke(country)
            }
        }

    }

}