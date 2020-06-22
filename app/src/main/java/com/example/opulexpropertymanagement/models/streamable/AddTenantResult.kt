package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.models.Tenant

sealed class AddTenantResult {
    class Success(val tenant: Tenant): AddTenantResult()
    sealed class Failure: AddTenantResult() {
        object Unknown: Failure()
        object CouldNotFindTenantJustAdded : Failure()
        object EmailAlreadyExists : Failure()
        object PropertyNotFound : Failure()
        object WrongLandlordID: Failure()
        object FailedToUploadImage: Failure()
    }
}