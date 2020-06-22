package com.example.opulexpropertymanagement.aa_repo

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.app.FBKEY_DOCUMENT
import com.example.opulexpropertymanagement.app.fbUserDBTable
import com.example.opulexpropertymanagement.app.fbUserStorageTable
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.models.streamable.AddDocumentResult
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.opulexpropertymanagement.util.createUniqueID
import com.example.tmcommonkotlin.logz
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference

class DocumentsRepo {
    // addDocument
    val addDocumentResult by lazy { MutableLiveData<AddDocumentResult>() }
    fun addDocument(tenantID: String, uri: Uri, title:String) {
        logz("addDocument`Open")
        val newDocumentRef = fbUserDBTable?.child(tenantID)?.child(FBKEY_DOCUMENT)?.push()!!
        val newDocumentID = newDocumentRef.key!!
        newDocumentRef.setValue(newDocumentID) // Apparently, an object is not generated if its value is null
        val newDocument = Document(newDocumentID, tenantID, title)
        fbUserStorageTable?.child(FBKEY_DOCUMENT)?.child(tenantID)?.child(newDocumentID)?.putFile(uri)
            ?.addOnSuccessListener {
                logz("Successfully added document")
                addDocumentResult.value = AddDocumentResult.Success(newDocument)
            }
            ?.addOnFailureListener {
                logz("Failed adding document")
                addDocumentResult.value = AddDocumentResult.Failure.Unknown(it)
            }
    }

    // getDocuments
    val streamGetDocumentsResponse by lazy { MutableLiveData<List<Document>>() } // TODO
    fun getDocuments(tenantID: String) {
        logz("getDocuments`Open")
        fbUserDBTable?.child(tenantID)?.child(FBKEY_DOCUMENT)
            ?.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) { }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.value!=null) {
                        val documents = (dataSnapshot.value as Map<String, Any>).map {
                            Document(it.key, tenantID, it.value.toString())
                        }
                        streamGetDocumentsResponse.value = documents
                        logz("documents:${streamGetDocumentsResponse.value}")
                    }
                }
            })
    }
}