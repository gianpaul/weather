package com.example.weathercoroutines.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Address
import com.example.weathercoroutines.databinding.FragmentHomeBinding
import com.example.weathercoroutines.maps.component.DialogItemWeather
import com.example.weathercoroutines.screens.home.adapter.AddressesAdapter
import com.example.weathercoroutines.screens.home.viewmodel.HomeViewModel
import com.example.weathercoroutines.utils.DefaultPlace
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialog: DialogItemWeather

    private val addressesAdapter = AddressesAdapter { address ->
        handleAddress(address)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupButtons()
        setupSearchPlace()
        loader()
        setupMap()
    }

    private fun setupButtons() {
        binding.btClearCache.setOnClickListener { viewModel.clearCache() }
    }

    private fun setupObserver() {
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.CacheCleared -> Toast.makeText(context, "Cache cleared", LENGTH_SHORT).show()
                is HomeState.Error -> Toast.makeText(context, "Something is wrong", LENGTH_SHORT).show()
                is HomeState.SuccessAddresses -> addressesAdapter.update(state.addresses.take(3))
                is HomeState.SuccessWeather -> {
                    dialog = DialogItemWeather(state.data)
                    dialog.show(parentFragmentManager, "DialogItemWeather")
                }
                is HomeState.UpdateAddress -> {
                    updateMapPosition(
                        state.address?.latitude,
                        state.address?.longitude
                    )
                }
            }
        }
    }

    private fun setupSearchPlace() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = addressesAdapter

        binding.address.doAfterTextChanged(lifecycle, debounce = 500) { text ->
            binding.recyclerView.isVisible = true
            viewModel.search(text)
        }
    }

    private fun setupMap() {
        binding.componentMap.initialize(childFragmentManager) {
            binding.componentMap.setCenter(
                DefaultPlace.FINVIVIR.lat,
                DefaultPlace.FINVIVIR.lon,
                showMarker = true
            )

            binding.componentMap.setOnLocationChangedListener { latitude, longitude ->
                getWeatherByCoordinates(latitude, longitude)
            }
        }
    }

    private fun getWeatherByCoordinates(latitude: Double?, longitude: Double?) {
        viewModel.getWeatherByCoordinates(latitude, longitude)
    }

    private fun updateMapPosition(latitude: Double?, longitude: Double?) {
        binding.componentMap.setCenter(latitude ?: 0.0, longitude ?: 0.0)
    }

    private fun handleAddress(address: Address) {
        viewModel.getPlaceDetails(address)
        binding.recyclerView.isVisible = false
    }

    private fun loader() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loading.receiveAsFlow().collect { isLoading ->
                binding.loader.isVisible = isLoading
            }
        }
    }

}