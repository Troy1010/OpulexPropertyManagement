package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.models.Property

sealed class RemovePropertyResult {
    data class Success(val propertyID:String): RemovePropertyResult()
    sealed class Failure: RemovePropertyResult() {
        object Unknown: Failure()
    }
}