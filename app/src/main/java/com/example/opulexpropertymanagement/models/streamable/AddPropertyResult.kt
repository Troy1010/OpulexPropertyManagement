package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.ac_ui.User

sealed class AddPropertyResult {
    class Success(val user: User): AddPropertyResult()
    sealed class Failure: AddPropertyResult() {
        object MismatchedUserIDVsType: Failure()
        object UserTypeShouldBeLandlord: Failure()
        object Unknown: Failure()
    }
}