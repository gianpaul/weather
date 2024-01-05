package com.example.weathercoroutines.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("AppCompatCustomView")
class DebounceEditText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : EditText(context, attributeSet, defStyleAttr) {

    private var searchJob: Job? = null

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun setOnDebounceTextWatcher(
        lifecycle: Lifecycle,
        debounce: Long = 600,
        onDebounceAction: (String) -> Unit
    ) {
        searchJob?.cancel()
        searchJob = onDebounceTextChanged()
            .debounce(debounce)
            .onEach { onDebounceAction(it) }
            .launchIn(lifecycle.coroutineScope)
    }

    fun removeOnDebounceTextWatcher() {
        searchJob?.cancel()
    }

    @ExperimentalCoroutinesApi
    private fun onDebounceTextChanged(): Flow<String> = channelFlow {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun afterTextChanged(p0: Editable?) {
                trySend(p0.toString()).isSuccess
            }
        }

        addTextChangedListener(textWatcher)

        awaitClose {
            removeTextChangedListener(textWatcher)
        }
    }
}