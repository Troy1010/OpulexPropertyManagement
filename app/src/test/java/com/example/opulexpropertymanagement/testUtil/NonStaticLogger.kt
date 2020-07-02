package com.example.opulexpropertymanagement.testUtil

import android.util.Log

// This non-static logger is usable during tests
class NonStaticLogger {
    fun e(tag: String?, message: String?) {
        Log.e(tag, message)
    }

    fun w(tag: String?, message: String?) {
        Log.w(tag, message)
    }

    fun v(tag: String?, message: String?) {
        Log.v(tag, message)
    }

    fun d(tag: String?, message: String?) {
        Log.d(tag, message)
    }
}