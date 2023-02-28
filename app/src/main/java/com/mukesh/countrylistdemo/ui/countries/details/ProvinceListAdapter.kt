package com.mukesh.countrylistdemo.ui.countries.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mukesh.countrylistdemo.data.model.Country
import com.mukesh.countrylistdemo.data.model.Province
import com.mukesh.countrylistdemo.databinding.RowProvinceBinding

/**
Created by Mukesh on 27/02/23
 Province list adapter
 **/

class ProvinceListAdapter: Adapter<ProvinceListAdapter.ProvinceViewHolder>() {
    private val provinceList = mutableListOf<Province>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinceViewHolder {
        val inflator= LayoutInflater.from(parent.context)
        val binding = RowProvinceBinding.inflate(inflator,parent,false)
        return ProvinceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProvinceViewHolder, position: Int) {
        holder.setData(provinceList[position])
    }

    override fun getItemCount(): Int {
        return provinceList.size
    }

    fun setData(list: List<Province>){
        provinceList.clear()
        provinceList.addAll(list)
        notifyItemRangeChanged(0,list.size)
    }


    inner class ProvinceViewHolder(private val binding: RowProvinceBinding): ViewHolder(binding.root) {
        fun setData(province: Province) {
            binding.name.text = province.stateName
        }

    }

}