package com.example.opulexpropertymanagement.layers.data_layer

import android.net.Uri
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.models.streamable.AddDocumentResult
import com.example.opulexpropertymanagement.models.streamable.RemoveDocumentResult
import com.example.opulexpropertymanagement.models.streamable.UpdateDocumentResult
import com.google.firebase.database.DatabaseError

interface IDocumentDataSource {
    suspend fun addDocument(tenantID: String, uri: Uri, title: String): AddDocumentResult

    suspend fun removeDocument(document: Document): RemoveDocumentResult

    suspend fun updateDocument(document: Document): UpdateDocumentResult

    fun setDocumentsChangedListener(
        tenantID: String,
        listener: (List<Document>) -> Unit,
        errorListener: ((DatabaseError) -> Unit)? =null
    )
}