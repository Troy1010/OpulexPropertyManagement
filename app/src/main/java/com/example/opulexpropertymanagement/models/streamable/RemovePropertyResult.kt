package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.models.Property

sealed class RemovePropertyResult {
    object Success: RemovePropertyResult()
    sealed class Failure: RemovePropertyResult() {
        object Unknown: Failure()
    }
}