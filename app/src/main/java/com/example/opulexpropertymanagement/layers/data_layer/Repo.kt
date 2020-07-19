package com.example.opulexpropertymanagement.layers.data_layer

import android.net.Uri
import com.example.opulexpropertymanagement.FBKEY_DOCUMENT
import com.example.opulexpropertymanagement.FBKEY_TENANT
import com.example.opulexpropertymanagement.fbUserDBTable
import com.example.opulexpropertymanagement.fbUserStorageTable
import com.example.opulexpropertymanagement.layers.z_ui.User
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.models.streamable.AddDocumentResult
import com.example.opulexpropertymanagement.models.streamable.RemoveDocumentResult
import com.example.opulexpropertymanagement.models.streamable.UpdateDocumentResult
import com.example.opulexpropertymanagement.util.generateUniqueID
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Repo @Inject constructor(private val sharedPrefWrapper: SharedPrefWrapper)
    : SharedPrefWrapperInterface by sharedPrefWrapper
{
    // create another firebase mutation repo
    suspend fun addDocument(tenantID: String, uri: Uri, title: String): AddDocumentResult {
        val newDocumentID = generateUniqueID()
        val newDocument = Document(newDocumentID, tenantID, title)
        try {
            fbUserStorageTable?.child(FBKEY_DOCUMENT)?.child(tenantID)?.child(newDocumentID)
                ?.putFile(uri)
                ?.await()
            fbUserDBTable?.child(FBKEY_TENANT)?.child(tenantID)?.child(FBKEY_DOCUMENT)?.child(newDocumentID)
                ?.setValue(title)
            return AddDocumentResult.Success(newDocument)
        } catch (e: Exception) {
            return AddDocumentResult.Failure.Unknown(e)
        }
    }

    suspend fun removeDocument(document: Document): RemoveDocumentResult {
        try {
            fbUserDBTable?.child(FBKEY_TENANT)?.child(document.tenantID)?.child(
                FBKEY_DOCUMENT
            )?.child(document.id)?.removeValue()?.await()
            return RemoveDocumentResult.Success(document)
        } catch (e: Exception) {
            return RemoveDocumentResult.Failure(document)
        }
    }

    suspend fun updateDocument(document: Document): UpdateDocumentResult {
        try {
            fbUserDBTable?.child(FBKEY_TENANT)?.child(document.tenantID)?.child(
                FBKEY_DOCUMENT
            )?.child(document.id)
                ?.setValue(document.title)
                ?.await()
            return UpdateDocumentResult.Success(document)
        } catch (e: Exception) {
            return UpdateDocumentResult.Failure.Unknown(e)
        }
    }

    // create another firebase query repo
    fun setDocumentsChangedListener(
        tenantID: String,
        listener: (List<Document>) -> Unit,
        errorListener: ((DatabaseError) -> Unit)?=null
    ) {
        fbUserDBTable?.child(FBKEY_TENANT)?.child(tenantID)?.child(FBKEY_DOCUMENT)
            ?.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    errorListener?.invoke(p0)
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val documents = if (dataSnapshot.value == null) {
                        emptyList()
                    } else {
                        @Suppress("UNCHECKED_CAST")
                        (dataSnapshot.value as Map<String, Any>).map {
                            Document(it.key, tenantID, it.value.toString())
                        }
                    }
                    listener(documents)
                }
            })
    }
}