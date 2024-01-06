package com.example.weathercoroutines.screens.localweather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathercoroutines.databinding.ItemWeatherBinding
import com.example.weathercoroutines.model.WeatherUiModel

class WeatherAdapter(
    private var weathers: List<WeatherUiModel> = emptyList(),
    private val clickListener: (WeatherUiModel) -> Unit
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(
        private val binding: ItemWeatherBinding,
        private val clickListener: (WeatherUiModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                clickListener(it.tag as WeatherUiModel)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(weather: WeatherUiModel) {
            binding.weatherId.text = weather.id.toString()
            binding.latLon.text = "${weather.lat + weather.lon}"
            binding.root.tag = weather
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weathers[position])
    }

    override fun getItemCount() = weathers.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(weathers: List<WeatherUiModel>) {
        this.weathers = weathers
        notifyDataSetChanged()
    }
}
