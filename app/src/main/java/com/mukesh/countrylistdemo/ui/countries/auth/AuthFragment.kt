package com.mukesh.countrylistdemo.ui.countries.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mukesh.countrylistdemo.data.repository.helper.Resource
import com.mukesh.countrylistdemo.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Mukesh on 27/02/23
 * Authentication fragment. Here we call the auth api and if auth token is received successfully then redirect to the country list fragmetn
 */
@AndroidEntryPoint
class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Observe the auth token live data
        viewModel.authToken.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.progress.visibility = View.VISIBLE
                }
                is Resource.Success ->{
                    binding.progress.visibility = View.GONE
                    navigateToCountryList()
                }
                is Resource.Error ->{
                    binding.progress.visibility = View.GONE
                    it.error?.message?.let { Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show() }

                }
            }
        }
    }

    /**
     * Navigate to the country list screen
     */
    private fun navigateToCountryList() {
        findNavController().navigate(AuthFragmentDirections.actionToCountryList())
    }

}