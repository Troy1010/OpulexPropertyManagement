package com.example.opulexpropertymanagement.aa.ui


sealed class ResultZ {
    data class Error(val msg: String) : ResultZ()
    object Success : ResultZ() {
        override fun toString(): String {
            return "ResultZ.Success"
        }
    }
}