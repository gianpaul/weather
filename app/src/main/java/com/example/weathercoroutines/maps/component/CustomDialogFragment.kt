package com.example.weathercoroutines.maps.component

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.weathercoroutines.R
import com.example.weathercoroutines.databinding.ItemWeatherDialogBinding
import com.example.weathercoroutines.model.WeatherUiModel

class DialogItemWeather(
    private val weatherUiModel: WeatherUiModel?
) : DialogFragment() {

    private lateinit var binding: ItemWeatherDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        binding = ItemWeatherDialogBinding.inflate(layoutInflater)


        setupDialog(weatherUiModel)


        builder.setView(binding.root)

        return builder.create()
    }

    private fun setupDialog(
        weatherUiModel: WeatherUiModel?
    ) = with(binding) {
        if (weatherUiModel == null) return@with dismiss()

        textViewLatitude.text = getString(R.string.latitude, weatherUiModel.lat.toString())
        textViewLongitude.text = getString(R.string.longitude, weatherUiModel.lon.toString())
        textViewTimezone.text = getString(R.string.time_zone, weatherUiModel.timezone)
        textViewClouds.text = getString(R.string.clouds, weatherUiModel.clouds.toString())
        textViewHumidity.text = getString(R.string.humidity, weatherUiModel.humidity.toString())
        textViewPressure.text = getString(R.string.pressure, weatherUiModel.pressure.toString())
        textViewSunrise.text = getString(R.string.sunrise, weatherUiModel.sunrise.toString())
        textViewSunset.text = getString(R.string.sunset, weatherUiModel.sunset.toString())
        textViewTemperature.text = getString(R.string.temperature, weatherUiModel.temp.toString())
        textViewUvi.text = getString(R.string.uvi, weatherUiModel.uvi.toString())
        textViewVisibility.text = getString(R.string.visibility, weatherUiModel.visibility.toString())

        close.setOnClickListener {
            dismiss()
        }
    }
}