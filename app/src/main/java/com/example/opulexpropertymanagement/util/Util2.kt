package com.example.opulexpropertymanagement.util

import java.util.*

@Throws(Exception::class)
fun createUniqueID(): String? {
    return UUID.randomUUID().toString().replace("-", "").toUpperCase()
}

fun DoTheOtherThing(): String {
    return "Pineapple collection complete"
}

fun DoTheOtherOtherThing(): String {
    return "Eggs hidden"
}