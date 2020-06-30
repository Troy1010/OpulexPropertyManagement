package com.example.opulexpropertymanagement.models.streamable

sealed class RemovePropertyResult {
    data class Success(val propertyID:String): RemovePropertyResult()
    sealed class Failure: RemovePropertyResult() {
        object Unknown: Failure()
    }
}