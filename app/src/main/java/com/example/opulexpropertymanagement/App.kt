package com.example.opulexpropertymanagement

import android.app.Application
import com.example.opulexpropertymanagement.di.ApplicationComponentZ
import com.example.opulexpropertymanagement.di.ApplicationModuleZ
import com.example.opulexpropertymanagement.di.DaggerApplicationComponentZ

val App by lazy { AppClass.instance }

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this // TODO remove
        appComponent = DaggerApplicationComponentZ.builder().applicationModuleZ(ApplicationModuleZ(this)).build()
    }
    lateinit var appComponent: ApplicationComponentZ
    companion object {
        lateinit var instance: AppClass
            private set
    }
}
