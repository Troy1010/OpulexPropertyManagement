package com.example.opulexpropertymanagement.layers.view_models

import android.net.Uri
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opulexpropertymanagement.layers.data_layer.Repo
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.models.streamable.AddDocumentResult
import com.example.opulexpropertymanagement.models.streamable.RemoveDocumentResult
import com.example.opulexpropertymanagement.models.streamable.UpdateDocumentResult
import com.example.opulexpropertymanagement.util.postResult
import com.example.tmcommonkotlin.logz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TenantDetailsVM(val tenant: Tenant, val repo: Repo) : ViewModel() {

    val documents by lazy { MediatorLiveData<List<Document>>() }

    init {
        repo.setDocumentsChangedListener(tenant.id, { documentsValue ->
            documents.value = documentsValue
        })
    }

    val removeDocumentResult by lazy { MediatorLiveData<RemoveDocumentResult>() }
    fun removeDocument(document: Document) {
        removeDocumentResult.postResult(viewModelScope) { repo.removeDocument(document) }
    }

    val addDocumentResult by lazy { MediatorLiveData<AddDocumentResult>() }
    fun addDocument(uri: Uri, title: String) {
        addDocumentResult.postResult(viewModelScope) { repo.addDocument(tenant.id, uri, title) }
    }

    val updateDocumentResult by lazy { MediatorLiveData<UpdateDocumentResult>() }
    fun updateDocument(document: Document) {
        updateDocumentResult.postResult(viewModelScope) { repo.updateDocument(document) }
    }

    fun finalize() {
        logz("TenantDetailsVM`finalize")
    }
}
