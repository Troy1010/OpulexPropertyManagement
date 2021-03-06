package com.example.opulexpropertymanagement.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.opulexpropertymanagement.R
import com.example.tmcommonkotlin.logx
import com.example.tmcommonkotlin.logz

class ActivitySplashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        setTheme(R.style.Splashscreen)
        logz("START")
        var handler = Handler()
        handler.postDelayed( {
            startLandingActivity()
        }, 1000)
    }

    private fun startLandingActivity() {
        startActivity(Intent(this, ActivityHost::class.java))
        finish()
    }
}