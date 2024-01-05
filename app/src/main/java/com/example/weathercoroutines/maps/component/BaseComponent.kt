package com.example.weathercoroutines.maps.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding

@Suppress("LeakingThis")
abstract class BaseComponent<VB : ViewBinding>(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : FrameLayout(context, attrs, defStyleAttr) {

    protected lateinit var binding: VB
    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    init {
        privateInit()
        postInitialization(attrs)
    }

    abstract fun postInitialization(attrs: AttributeSet?)

    private fun privateInit() {
        binding = bindingInflater.invoke(LayoutInflater.from(context), this, false)
        addView(binding.root)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        binding.root.setOnClickListener(listener)
    }
}