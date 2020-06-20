package com.example.opulexpropertymanagement.models.streamable

sealed class AddPropertyResult {
    object Success: AddPropertyResult()
    sealed class Failure: AddPropertyResult() {
        object MismatchedUserIDVsType: Failure()
        object UserTypeShouldBeLandlord: Failure()
        object Unknown: Failure()
    }
}