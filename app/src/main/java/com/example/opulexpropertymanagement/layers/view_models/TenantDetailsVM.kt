package com.example.opulexpropertymanagement.layers.view_models

import android.net.Uri
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opulexpropertymanagement.layers.data_layer.Repo
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.models.streamable.AddDocumentResult
import com.example.opulexpropertymanagement.models.streamable.RemoveDocumentResult
import com.example.opulexpropertymanagement.models.streamable.UpdateDocumentResult
import com.example.tmcommonkotlin.logz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TenantDetailsVM(val tenant: Tenant, val repo: Repo) : ViewModel() {

    val documents by lazy { MediatorLiveData<List<Document>>() }

    init {
        repo.documentsChangedListener(tenant.id, { dataSnapshot ->
            if (dataSnapshot.value != null) {
                val documentsMap = (dataSnapshot.value as Map<String, Any>).map {
                    Document(it.key, tenant.id, it.value.toString())
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
        logz("uri:$uri")
        viewModelScope.launch {
            val result = repo.addDocument(
                tenant.id,
                uri,
                title
            )
            withContext(Dispatchers.Main) {
                addDocumentResult.value = result
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