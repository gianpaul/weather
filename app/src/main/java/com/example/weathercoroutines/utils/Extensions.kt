package com.example.weathercoroutines.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.Dimension
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

fun View.dpToPx(@Dimension(unit = Dimension.DP) dp: Int): Int {
    val r = context.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
        .toInt()
}