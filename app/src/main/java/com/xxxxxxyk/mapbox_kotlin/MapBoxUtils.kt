package com.xxxxxxyk.mapbox_kotlin

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.content.ContextCompat
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.services.android.telemetry.location.LocationEngineListener
import org.jetbrains.anko.toast

/**
 * Created by 惜梦哥哥 on 2017/12/20.
 */
class MapBoxUtils private constructor(context: Context, mapView: MapView) {

    private var mapbox = mapView
    private var context = context

    companion object {
        @Volatile
        private var instance: MapBoxUtils? = null

        fun getInstance(context: Context, m: MapView): MapBoxUtils {
            if (instance == null) {
                synchronized(MapBoxUtils::class) {
                    if (instance == null) {
                        instance = MapBoxUtils(context, m)
                    }
                }
            }
            return instance!!
        }
    }

    fun moveToLocation(latLng: LatLng) {
        moveToLocation(latLng, false)
    }

    fun moveToLocation(latLng: LatLng, boolean: Boolean) {
        if (boolean) {
            moveToLocation(latLng, 2000)
        } else {
            mapbox.getMapAsync { it.moveCamera(CameraUpdateFactory.newLatLng(latLng)) }
        }
    }

    fun moveToLocation(latLng: LatLng, durationMs: Int) {
        mapbox.getMapAsync { it.animateCamera(CameraUpdateFactory.newLatLng(latLng), durationMs) };
    }

    fun moveToLocationZoom(latLng: LatLng, zoom: Double) {
        mapbox.getMapAsync { it.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latLng), zoom), 1000) }
    }


    @SuppressLint("MissingPermission")
    fun moveToCurrentLocation(): Boolean {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            context.toast("您未允许定位权限,无法更新到您的位置")
            return false
        }

        mapbox.getMapAsync {

            val locationEngine = Mapbox.getLocationEngine()
            locationEngine.activate()

            val lastLocation = locationEngine.lastLocation

            lastLocation?.let {
                moveToLocationZoom(LatLng(lastLocation), 15.0)
            }

            locationEngine.addLocationEngineListener(object : LocationEngineListener {
                override fun onLocationChanged(location: Location?) {
                    location?.let {
                        moveToLocationZoom(LatLng(location), 15.0)
                        drawPoint(LatLng(location))
                    }
                }

                override fun onConnected() {
                    //链接定位服务
                }
            })
        }
        return true
    }

    fun drawPoint(position: LatLng) {
        mapbox.getMapAsync {
            it.addMarker(MarkerOptions().position(position).icon(IconFactory.getInstance(context).fromResource(R.mipmap.s_location)))
        }
    }
}