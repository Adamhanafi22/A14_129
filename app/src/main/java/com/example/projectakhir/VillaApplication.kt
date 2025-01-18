package com.example.projectakhir

import android.app.Application
import com.example.projectakhir.container.AppContainer
import com.example.projectakhir.container.VillaContainer

class VillaApplications: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = VillaContainer()
    }
}