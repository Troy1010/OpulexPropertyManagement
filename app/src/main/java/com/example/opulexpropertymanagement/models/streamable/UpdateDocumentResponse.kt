package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.models.Document
import java.lang.Exception

sealed class UpdateDocumentResult {
    class Success(val document: Document): UpdateDocumentResult()
    sealed class Failure: UpdateDocumentResult() {
        class Unknown(e: Exception): Failure()
    }
}