package com.example.weathercoroutines.screens.localweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathercoroutines.databinding.FragmentLocalWeatherBinding
import com.example.weathercoroutines.maps.component.DialogItemWeather
import com.example.weathercoroutines.model.WeatherUiModel
import com.example.weathercoroutines.screens.localweather.adapter.WeatherAdapter
import com.example.weathercoroutines.screens.localweather.viewmodel.LocalWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocalWeatherFragment : Fragment() {

    private val viewModel: LocalWeatherViewModel by viewModels()
    private lateinit var binding: FragmentLocalWeatherBinding
    private lateinit var dialog: DialogItemWeather

    private val weatherAdapter: WeatherAdapter by lazy {
        WeatherAdapter { weather ->
            handleWeather(weather)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocalWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupButtons()
        loader()
    }

    private fun setupButtons() {
        binding.btClearCache.setOnClickListener { viewModel.clearCache() }
    }

    private fun setupObserver() {
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                LocalWeatherState.CacheCleared -> {
                    Toast.makeText(context, "Cache cleared", LENGTH_SHORT).show()
                    weatherAdapter.update(emptyList())
                }
                is LocalWeatherState.Error -> Toast.makeText(context, "Something is wrong", LENGTH_SHORT).show()
                is LocalWeatherState.SuccessWeather -> setupWeatherAdapter(state.data)
            }
        }
    }

    private fun setupWeatherAdapter(weathers: List<WeatherUiModel>?) {
        weathers?.let {
            println(weathers)
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = weatherAdapter
            weatherAdapter.update(it)
        }
    }

    private fun handleWeather(weather: WeatherUiModel) {
        dialog = DialogItemWeather(weather)
        dialog.show(parentFragmentManager, "DialogItemWeather")
    }

    private fun loader() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loading.receiveAsFlow().collect { isLoading ->
                binding.loader.isVisible = isLoading
            }
        }
    }

}