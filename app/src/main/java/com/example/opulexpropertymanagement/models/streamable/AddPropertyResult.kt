package com.example.opulexpropertymanagement.models.streamable

sealed class AddPropertyResult {
    object Success: AddPropertyResult()
    object Failure: AddPropertyResult()
}