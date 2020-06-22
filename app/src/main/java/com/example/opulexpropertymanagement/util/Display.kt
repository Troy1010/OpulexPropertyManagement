package com.example.opulexpropertymanagement.util

object Display {
    fun asMoney(s:String): String {
        return "$${s.reversed().chunked(3).joinToString(",").reversed()}"
    }
}