package com.example.opulexpropertymanagement.aa_repo

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.app.FBKEY_DOCUMENT
import com.example.opulexpropertymanagement.models.Maintenance

class MaintenancesRepo {
    // addDocument
    val addMaintenanceResult by lazy { MutableLiveData<List<Maintenance>>() }
    fun addMaintenance(tenantID: String, maintenance: Maintenance) {

    }

//    fun addDocument(tenantID: String, uri: Uri, title:String) {
//        logz("addDocument`Open")
//        val newDocumentRef = fbUserDBTable?.child(tenantID)?.child(FBKEY_DOCUMENT)?.push()!!
//        val newDocumentID = newDocumentRef.key!!
//        newDocumentRef.setValue(title)
//        val newDocument = Document(newDocumentID, tenantID, title)
//        fbUserStorageTable?.child(FBKEY_DOCUMENT)?.child(tenantID)?.child(newDocumentID)?.putFile(uri)
//            ?.addOnSuccessListener {
//                logz("Successfully added document")
//                addDocumentResult.value = AddDocumentResult.Success(newDocument)
//            }
//            ?.addOnFailureListener {
//                logz("Failed adding document")
//                addDocumentResult.value = AddDocumentResult.Failure.Unknown(it)
//            }
//    }
//
//    // removeDocument
//    val removeDocumentResult by lazy { MutableLiveData<RemoveDocumentResult>() }
//    fun removeDocument(document: Document) {
//        fbUserDBTable?.child(document.tenantID)?.child(FBKEY_DOCUMENT)?.child(document.id)?.removeValue()
//            ?.addOnSuccessListener {
//                fbUserStorageTable?.child(FBKEY_DOCUMENT)?.child(document.tenantID)?.child(document.id)?.delete()
//                    ?.addOnSuccessListener {
//                        logz("Successfully removed document")
//                        removeDocumentResult.value = RemoveDocumentResult.Success(document)
//                    }
//                    ?.addOnFailureListener {
//                        removeDocumentResult.value = RemoveDocumentResult.Failure(document)
//                    }
//            }
//            ?.addOnFailureListener {
//                removeDocumentResult.value = RemoveDocumentResult.Failure(document)
//            }
//    }
//
//    // UpdateDocument
//    val updateDocumentResult by lazy { MutableLiveData<UpdateDocumentResult>() }
//    fun updateDocument(document: Document) {
//        fbUserDBTable?.child(document.tenantID)?.child(FBKEY_DOCUMENT)?.child(document.id)?.setValue(document.title)
//            ?.addOnSuccessListener {
//                updateDocumentResult.value = UpdateDocumentResult.Success(document)
//            }
//            ?.addOnFailureListener {
//                updateDocumentResult.value = UpdateDocumentResult.Failure.Unknown(it)
//            }
//    }
//
//    // getDocuments
//    val streamGetDocumentsResponse by lazy { MutableLiveData<List<Document>>() }
//    fun getDocuments(tenantID: String) {
//        logz("getDocuments`Open")
//        fbUserDBTable?.child(tenantID)?.child(FBKEY_DOCUMENT)
//            ?.addListenerForSingleValueEvent(object: ValueEventListener {
//                override fun onCancelled(p0: DatabaseError) { }
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    if (dataSnapshot.value!=null) {
//                        val documents = (dataSnapshot.value as Map<String, Any>).map {
//                            Document(it.key, tenantID, it.value.toString())
//                        }
//                        streamGetDocumentsResponse.value = documents
//                        logz("documents:${streamGetDocumentsResponse.value}")
//                    }
//                }
//            })
//    }
}