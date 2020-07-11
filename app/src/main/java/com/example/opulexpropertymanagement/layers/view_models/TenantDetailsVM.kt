package com.example.opulexpropertymanagement.layers.view_models

import android.net.Uri
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opulexpropertymanagement.App
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.models.streamable.AddDocumentResult
import com.example.opulexpropertymanagement.models.streamable.RemoveDocumentResult
import com.example.opulexpropertymanagement.models.streamable.UpdateDocumentResult
import com.example.tmcommonkotlin.logz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TenantDetailsVM(val tenant: Tenant) : ViewModel() {

    private val repo = App.component.getRepo()
    val documents by lazy { MediatorLiveData<List<Document>>() }

    init {
        val tenantID = tenant.id
        repo.documentsChangedListener(tenantID, { dataSnapshot ->
            if (dataSnapshot.value != null) {
                val documentsMap = (dataSnapshot.value as Map<String, Any>).map {
                    Document(it.key, tenantID, it.value.toString())
                }
                documents.value = documentsMap
            } else {
                documents.value = emptyList()
            }
        })
    }

    val removeDocumentResult by lazy { MediatorLiveData<RemoveDocumentResult>() }
    fun removeDocument(document: Document) {
        viewModelScope.launch {
            val result = repo.removeDocument(document)
            withContext(Dispatchers.Main) {
                removeDocumentResult.value = result
            }
        }
    }

    val addDocumentResult by lazy { MediatorLiveData<AddDocumentResult>() }
    fun addDocument(uri: Uri, title: String) {
        viewModelScope.launch {
            val tenantID = tenant.id
            if (tenantID != null) {
                val result = repo.addDocument(
                    tenantID,
                    uri,
                    title
                )
                withContext(Dispatchers.Main) {
                    addDocumentResult.value = result
                }
            }
        }
    }

    val updateDocumentResult by lazy { MediatorLiveData<UpdateDocumentResult>() }
    fun updateDocument(document: Document) {
        viewModelScope.launch {
            val result = repo.updateDocument(document)
            withContext(Dispatchers.Main) {
                updateDocumentResult.value = result
            }
        }
    }


    fun finalize() {
        logz("TenantDetailsVM`finalize")
    }
}