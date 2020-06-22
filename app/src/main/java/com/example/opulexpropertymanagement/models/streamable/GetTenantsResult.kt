package com.example.opulexpropertymanagement.models.streamable

sealed class GetTenantsResult {
    object Success: GetTenantsResult()
    object Failure: GetTenantsResult()
}