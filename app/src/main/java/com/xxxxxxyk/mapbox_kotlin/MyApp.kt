package com.xxxxxxyk.mapbox_kotlin

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

/**
 * Created by 惜梦哥哥 on 2017/12/20.
 */
class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(applicationContext, Config.KEY)
    }
}