package com.example.opulexpropertymanagement

import android.app.Application
import com.example.opulexpropertymanagement.di.ApplicationComponentZ
import com.example.opulexpropertymanagement.di.ApplicationModuleZ
import com.example.opulexpropertymanagement.di.DaggerApplicationComponentZ

val App by lazy { AppClass.instance }

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerApplicationComponentZ.builder().applicationModuleZ(ApplicationModuleZ()).build()
    }
    lateinit var component: ApplicationComponentZ
    companion object {
        lateinit var instance: AppClass
            private set
    }
}