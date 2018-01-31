package com.xxxxxxyk.mapbox_kotlin

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.xxxxxxyk.mapbox_kotlin.ViewToKotin.floatingActionButton
import com.xxxxxxyk.mapbox_kotlin.ViewToKotin.mapView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    lateinit var mapView: MapView
    lateinit var rl: FloatingActionButton
    lateinit var mapBoxUtils: MapBoxUtils
    private val PERMISSIONS_REQUEST_LOCATION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        relativeLayout {
            mapView = mapView {
                id = ViewID.MAP_ID
                setStyleUrl(getResources().getString(R.string.mapbox_style_satellite_streets))
            }.lparams(width = matchParent, height = matchParent)

            rl = floatingActionButton {
                setImageResource(R.mipmap.location)
                setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary))
                rippleColor = Color.parseColor("#0077FF")
                onClick {
                    if (mapBoxUtils.moveToCurrentLocation()) {
                        Snackbar.make(rl, "定位到了当前位置", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }.lparams(width = wrapContent, height = wrapContent) {
                alignParentRight()
                alignParentBottom()
                bottomMargin = 50
                rightMargin = 50
            }
        }

        mapView.onCreate(savedInstanceState);

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart();
        mapBoxUtils = MapBoxUtils.getInstance(this@MainActivity, mapView)

        //检查权限,未赋予权限则移动到指定位置
        if (mapBoxUtils.moveToCurrentLocation()) {
            Snackbar.make(rl, "定位到了当前位置", Snackbar.LENGTH_SHORT).show()
        } else {
            mapBoxUtils.moveToLocation(LatLng(40.0115402, 116.2779696))
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_LOCATION);
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION ->
                if (grantResults.isNotEmpty()) {
                    mapBoxUtils.moveToCurrentLocation()
                } else {
                    toast("您拒绝了定位权限,无法更新到您当前位置")
                }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState!!)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}
