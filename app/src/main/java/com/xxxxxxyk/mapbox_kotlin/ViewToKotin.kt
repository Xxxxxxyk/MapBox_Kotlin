package com.xxxxxxyk.mapbox_kotlin

import android.app.Activity
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import com.mapbox.mapboxsdk.maps.MapView
import org.jetbrains.anko.AnkoViewDslMarker
import org.jetbrains.anko._RelativeLayout
import org.jetbrains.anko.custom.ankoView

/**
 * Created by 惜梦哥哥 on 2017/12/20.
 */
internal object ViewToKotin {

    //让Anko支持mapbox

    public final inline fun ViewManager.mapView(theme: Int = 0) = mapView(theme) {}

    public final inline fun ViewManager.mapView(theme: Int = 0, init: MapView.() -> Unit) = ankoView({ MapView(it) }, theme, init)

    public final inline fun ViewManager.floatingActionButton(theme: Int = 0) = floatingActionButton(theme) {}

    public final inline fun ViewManager.floatingActionButton(theme: Int = 0, init: FloatingActionButton.() -> Unit) = ankoView({ FloatingActionButton(it) }, theme, init)

}