package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.models.Document
import java.lang.Exception


sealed class AddDocumentResult {
    class Success(val document: Document): AddDocumentResult()
    sealed class Failure: AddDocumentResult() {
        class Unknown(e: Exception): Failure()
    }
}