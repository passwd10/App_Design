package com.example.myapplication2

import android.app.Application
import com.facebook.stetho.Stetho

class Appication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}