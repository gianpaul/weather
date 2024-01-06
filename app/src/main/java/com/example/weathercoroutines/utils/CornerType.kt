package com.example.weathercoroutines.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

private const val CROSS_FADE_DURATION = 300
private const val BLUR_RADIUS = 25
private const val SAMPLING = 3
private const val BASE_URL = "https://openweathermap.org/img/wn/"

sealed class CornerType {
    object None : CornerType()
    object Circle : CornerType()
    data class All(val radius: Int = RADIUS_SMALL) : CornerType()
    data class Top(val radius: Int) : CornerType()
    data class AllTv(val radius: Int) : CornerType()

    companion object {
        const val RADIUS_SMALL = 8
    }
}

fun ImageView.download(
    item: String?,
    cornerType: CornerType = CornerType.None,
    isBlur: Boolean = false
) {
    if (item == null) return
    var requestOptions = when (cornerType) {
        is CornerType.All -> RequestOptions.bitmapTransform(
            RoundedCornersTransformation(
                dpToPx(cornerType.radius),
                0,
                RoundedCornersTransformation.CornerType.ALL
            )
        )

        is CornerType.AllTv -> RequestOptions.bitmapTransform(
            MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    dpToPx(cornerType.radius),
                    0,
                    RoundedCornersTransformation.CornerType.ALL
                )
            )
        )

        is CornerType.None -> RequestOptions()
        is CornerType.Circle -> RequestOptions.circleCropTransform()
        is CornerType.Top -> RequestOptions.bitmapTransform(
            MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(
                    dpToPx(cornerType.radius),
                    0,
                    RoundedCornersTransformation.CornerType.TOP
                )
            )
        )
    }
    requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)

    val itemWithUrl = "$BASE_URL$item.png"

    if (isBlur) {
        Glide.with(context)
            .load(itemWithUrl)
            .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION))
            .transform(BlurTransformation(BLUR_RADIUS, SAMPLING))
            .apply(requestOptions)
            .into(this)
    } else {
        Glide.with(context)
            .load(itemWithUrl)
            .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION))
            .apply(requestOptions)
            .into(this)
    }
}