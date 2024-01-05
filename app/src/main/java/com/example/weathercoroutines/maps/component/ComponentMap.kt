package com.example.weathercoroutines.maps.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.weathercoroutines.R
import com.example.weathercoroutines.databinding.ComponentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ComponentMap @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseComponent<ComponentMapBinding>(context, attrs, defStyleAttr), OnMapReadyCallback {

    companion object {
        const val DEFAULT_ZOOM = 17F
    }

    private lateinit var mapConfiguration: MapConfiguration
    private var onMapReadyCallback: (() -> Unit)? = null
    private var googleMap: GoogleMap? = null
    private var fragment: SupportMapFragment? = null
    private val pinIcon: BitmapDescriptor by lazy {
        val circleDrawable = ContextCompat.getDrawable(context, R.drawable.ic_location_map_40)
            ?: error("Shouldn't happen")
        getMarkerIconFromDrawable(circleDrawable)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ComponentMapBinding
        get() = ComponentMapBinding::inflate

    override fun postInitialization(attrs: AttributeSet?) {
        mapConfiguration = MapConfiguration.populate(context, attrs)
    }

    fun initialize(fragmentManager: FragmentManager, onMapReady: () -> Unit) {
        val mapFragment = SupportMapFragment.newInstance()
        fragmentManager
            .beginTransaction()
            .add(binding.mapContainer.id, mapFragment)
            .commit()
        this.onMapReadyCallback = onMapReady
        fragment = mapFragment
        fragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.uiSettings.isScrollGesturesEnabled = mapConfiguration.touchEnabled

        onMapReadyCallback?.invoke()
    }

    override fun onDetachedFromWindow() {
        fragment = null
        googleMap = null
        super.onDetachedFromWindow()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setCenter(latitude: Double, longitude: Double, showMarker: Boolean = true) {
        val googleMap = googleMap ?: return
        val location = LatLng(latitude, longitude)
        if (showMarker) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .icon(pinIcon)
            )
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM))
    }

    private fun getMarkerIconFromDrawable(drawable: Drawable): BitmapDescriptor {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun setOnLocationChangedListener(function: (latitude: Double, longitude: Double) -> Unit) {
        val googleMap = this.googleMap ?: return
        googleMap.setOnCameraIdleListener {
            val midLatLng: LatLng = googleMap.cameraPosition.target
            function.invoke(midLatLng.latitude, midLatLng.longitude)
        }
    }

    fun setOnMapClickListener(function: (latitude: Double, longitude: Double) -> Unit) {
        val googleMap = this.googleMap ?: return
        googleMap.setOnMapClickListener {
            val midLatLng: LatLng = it
            function.invoke(midLatLng.latitude, midLatLng.longitude)
        }
    }
}