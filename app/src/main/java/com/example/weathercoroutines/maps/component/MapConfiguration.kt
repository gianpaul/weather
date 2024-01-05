package com.example.weathercoroutines.maps.component

import android.content.Context
import android.util.AttributeSet
import com.example.weathercoroutines.R

data class MapConfiguration(
    val touchEnabled: Boolean = true
) {
    companion object {
        fun populate(context: Context, attrs: AttributeSet?): MapConfiguration {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ComponentMap)
            val configuration = MapConfiguration(
                touchEnabled = typedArray.getBoolean(R.styleable.ComponentMap_mapTouchEnabled, true)
            )
            typedArray.recycle()
            return configuration
        }
    }
}