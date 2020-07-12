package com.example.opulexpropertymanagement

import android.app.Application
import com.example.opulexpropertymanagement.di.AppComponent
import com.example.opulexpropertymanagement.di.AppModule
import com.example.opulexpropertymanagement.di.DaggerAppComponent

val App by lazy { AppClass.instance }

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this // TODO remove
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
    lateinit var appComponent: AppComponent
    companion object {
        lateinit var instance: AppClass
            private set
    }
}
