package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.models.Document

sealed class RemoveDocumentResult(val document: Document) {
    class Success(document: Document): RemoveDocumentResult(document)
    class Failure(document: Document): RemoveDocumentResult(document)
}