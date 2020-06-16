package com.example.opulexpropertymanagement.app

import android.app.Application

val App by lazy { AppClass.instance }

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object {
        lateinit var instance: AppClass
            private set
    }
}