package com.mukesh.countrylistdemo.ui.countries.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.countrylistdemo.data.model.Country
import com.mukesh.countrylistdemo.databinding.FragmentCountryListBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Mukesh on 27/02/23
 * Country list fragment to show the country list on UI
 */

@AndroidEntryPoint
class CountryListFragment : Fragment() {
    private val viewModel: CountryListViewModel by viewModels()
    private lateinit var binding: FragmentCountryListBinding
    private val adapter: CountryListAdapter by lazy { CountryListAdapter() }

    companion object{
        private val TAG = CountryListFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            layoutManager.orientation
        )
        binding.recyclerView.layoutManager= layoutManager
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        adapter.onItemClick = {viewModel.triggerEvent(CountryListViewModel.Event.CountrySelectedEvent(it))}
        binding.recyclerView.adapter = adapter

        //Observe the loader status to show the progress on UI
        viewModel.dataLoadingProgress.observe(viewLifecycleOwner){
            binding.progress.visibility = if (it)View.VISIBLE else View.GONE
        }
        //Observe the country list live data and set the list in adapter
        viewModel.countryList.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        //Observe the event flow for any event
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect{ event ->
                when(event){
                    is CountryListViewModel.Event.ErrorEvent ->{
                        Toast.makeText(requireContext(), event.message,Toast.LENGTH_LONG).show()
                    }
                    is CountryListViewModel.Event.CountrySelectedEvent -> {
                        navigateToDetailsScreen(country = event.country)
                    }
                }
            }
        }

    }

    /**
     * @param country
     * Navigate to the details screen
     */
    private fun navigateToDetailsScreen(country: Country?) {
        country?.countryName?.let {
            val directions = CountryListFragmentDirections.listToDetailsFragment(it)
            findNavController().navigate(directions)
        }
    }


}