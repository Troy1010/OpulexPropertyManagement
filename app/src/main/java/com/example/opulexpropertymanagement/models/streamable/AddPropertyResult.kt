package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.aa.ui.User
import com.example.opulexpropertymanagement.models.Property

sealed class AddPropertyResult {
    class Success(val user: User, val property: Property): AddPropertyResult()
    sealed class Failure: AddPropertyResult() {
        object MismatchedUserIDVsType: Failure()
        object UserTypeShouldBeLandlord: Failure()
        object DidNotReceiveProjectID: Failure()
        object FailedUploadingImageToFirebase: Failure()
        object Unknown: Failure()
    }
}