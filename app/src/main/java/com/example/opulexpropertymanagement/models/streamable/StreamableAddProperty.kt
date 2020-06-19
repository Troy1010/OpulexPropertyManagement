package com.example.opulexpropertymanagement.models.streamable

sealed class StreamableAddProperty {
    object Success: StreamableAddProperty()
    object Failure: StreamableAddProperty()
}