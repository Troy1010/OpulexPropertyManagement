package com.example.opulexpropertymanagement.zzTestUtil

import android.util.Log

// This non-static logger is usable during tests
open class NonStaticLogger {
    open fun e(tag: String?, message: String?) {
        Log.e(tag, message?:"")
    }

    open fun w(tag: String?, message: String?) {
        Log.w(tag, message?:"")
    }

    open fun v(tag: String?, message: String?) {
        Log.v(tag, message?:"")
    }

    open fun d(tag: String?, message: String?) {
        Log.d(tag, message?:"")
    }
}

class TestingLogger : NonStaticLogger() {
    override fun e(tag: String?, message: String?) {
        System.out.println("E " + tag + ": " + message);
    }
}