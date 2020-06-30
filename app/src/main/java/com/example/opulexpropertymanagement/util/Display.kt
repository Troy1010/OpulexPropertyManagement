package com.example.opulexpropertymanagement.util

object Display {
    fun asMoney(s:String): String {
        return "$${s.reversed().chunked(3).joinToString(",").reversed()}"
    }
    fun asStaredEmail(s:String): String {
        return if (s.length>4) {
            s.replaceRange(0,4, "****")
        } else {
            s
        }
    }
}