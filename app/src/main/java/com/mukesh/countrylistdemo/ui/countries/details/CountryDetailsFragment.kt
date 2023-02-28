package com.mukesh.countrylistdemo.ui.countries.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.countrylistdemo.R
import com.mukesh.countrylistdemo.databinding.FragmentCountryDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Mukesh on 27/02/23
 * Country details fragment is used to show the province list of the selected country
 */

@AndroidEntryPoint
class CountryDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCountryDetailsBinding
    private val viewModel: CountryDetailsViewModel by viewModels()
    private val adapter: ProvinceListAdapter by lazy { ProvinceListAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            val selectedCountry = arguments?.getString("selectedCountryName")
            selectedCountry?.let { it1 -> viewModel.setSelectedCountryName(it1) }
        }
        //set header text
        binding.headerText.text = getString(R.string.country_details_header_text,viewModel.selectedCountryName)
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            layoutManager.orientation
        )
        binding.provinceRecyclerView.layoutManager= layoutManager
        binding.provinceRecyclerView.addItemDecoration(dividerItemDecoration)
        binding.provinceRecyclerView.adapter = adapter

        //Observe loading progress to show the progress bar on UI
        viewModel.dataLoadingProgress.observe(viewLifecycleOwner){
            binding.progress.visibility = if (it)View.VISIBLE else View.GONE
        }
        //Observe province list and set the data in adapter
        viewModel.provinceList.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        //Observe the event flow for any event
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect{ event ->
                when(event){
                    is CountryDetailsViewModel.Event.ErrorEvent ->{
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


}