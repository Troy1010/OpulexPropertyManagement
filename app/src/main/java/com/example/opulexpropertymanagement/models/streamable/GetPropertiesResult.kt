package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.models.Property

sealed class GetPropertiesResult {
    data class Success(val properties: List<Property>): GetPropertiesResult()
    sealed class Failure: GetPropertiesResult() {
        object UserNotALandlord: Failure()
        object InvalidUserID: Failure()
        object Unknown: Failure()
    }
}