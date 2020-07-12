package com.example.opulexpropertymanagement

import android.app.Application
import com.example.opulexpropertymanagement.di.AppModule
import com.example.opulexpropertymanagement.di.DaggerAppComponent

class App : Application() {
    val appComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}
