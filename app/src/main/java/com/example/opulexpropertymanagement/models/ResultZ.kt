package com.example.pg_mvvm.models


sealed class ResultZ {
    data class Error(val msg: String) : ResultZ()
    object Success : ResultZ() {
        override fun toString(): String {
            return "ResultZ.Success"
        }
    }
}