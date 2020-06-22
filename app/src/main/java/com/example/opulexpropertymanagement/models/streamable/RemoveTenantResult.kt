package com.example.opulexpropertymanagement.models.streamable

sealed class RemoveTenantResult {
    data class Success(val tenantID:String): RemoveTenantResult()
    sealed class Failure: RemoveTenantResult() {
        object ApiDoesNotSupportRemovingTenants :Failure()
        object Unknown: Failure()
    }
}