package com.example.weathercoroutines.maps.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import com.example.weathercoroutines.R
import com.example.weathercoroutines.databinding.ComponentFormEdittextBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

data class TextState(
    val text: String? = null
)

class ComponentFormEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseComponent<ComponentFormEdittextBinding>(context, attrs, defStyleAttr) {

    private var textState: TextState? = null
    private var textHint: CharSequence? = null
    private var startIcon: Drawable? = null
    private var tintColor: Int = 0
    private var tintColorHint: Int = 0

    override val bindingInflater: (
        LayoutInflater,
        ViewGroup?,
        Boolean
    ) -> ComponentFormEdittextBinding
        get() = ComponentFormEdittextBinding::inflate

    override fun postInitialization(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ComponentFormEditText)
        tintColor =
            a.getColor(
                R.styleable.ComponentFormEditText_form_tintColor,
                ContextCompat.getColor(context, R.color.textPrimary)
            )
        tintColorHint =
            a.getColor(
                R.styleable.ComponentFormEditText_form_tintColorHint,
                ContextCompat.getColor(context, R.color.textPrimaryMuted)
            )
        textHint = a.getString(R.styleable.ComponentFormEditText_form_hint)
        startIcon = a.getDrawable(R.styleable.ComponentFormEditText_form_iconStart)
        initializeComponent()

        a.recycle()
    }

    private fun initializeComponent() {
        binding.editText.doAfterTextChanged {
            textState = TextState(it.toString())
            if (it.isNullOrEmpty()) {
                binding.clear.isVisible = false
                binding.editText.setHintTextColor(tintColorHint)
            } else {
                binding.clear.isVisible = true
                binding.editText.setHintTextColor(tintColor)
            }
        }
        binding.clear.setOnClickListener {
            binding.editText.setText("")
        }
        binding.editText.hint = textHint
    }

    fun setText(text: String?) {
        if (textState?.text.orEmpty() != text.orEmpty()) {
            binding.editText.setText(text)
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun doAfterTextChanged(
        lifecycleScope: Lifecycle,
        debounce: Long = 500L,
        afterTextChanged: (String) -> Unit
    ) {
        binding.editText.setOnDebounceTextWatcher(lifecycleScope, debounce, afterTextChanged)
    }

    override fun onDetachedFromWindow() {
        binding.editText.removeOnDebounceTextWatcher()
        binding.editText.onFocusChangeListener = null
        super.onDetachedFromWindow()
    }
}