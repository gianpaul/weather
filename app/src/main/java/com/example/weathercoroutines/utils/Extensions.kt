package com.example.weathercoroutines.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.round(num: Int): Double {
    val factor = 10.0.pow(num.toDouble())
    return (this * factor).roundToInt() / factor
}

fun View.hideKeyboard() {
    val imm: InputMethodManager? =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(this.windowToken, 0)
}