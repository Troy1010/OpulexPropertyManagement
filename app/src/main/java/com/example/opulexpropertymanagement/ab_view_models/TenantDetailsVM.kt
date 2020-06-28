package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.navigation.NavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.aa_repo.DocumentsRepo
import com.example.opulexpropertymanagement.ab_view_models.inheritables.FragmentallyScopedVM
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.models.streamable.AddDocumentResult
import com.example.opulexpropertymanagement.models.streamable.UpdateDocumentResult
import com.example.tmcommonkotlin.logz

class TenantDetailsVM(val navController: NavController): FragmentallyScopedVM(navController, ::instanceAsFragmentallyScopedVM) {
    override val fragmentsToScopeWith = hashSetOf(R.id.tenantDetailsFrag, R.id.documentDetailsFrag)
    companion object {
        // make a nullable singleton instance of TenantDetailsVM
        @Volatile
        private var instance: TenantDetailsVM?=null
        private val LOCK = Any()
        fun getInstance(navController:NavController): TenantDetailsVM {
            return instance?: synchronized(LOCK) {
                instance?:TenantDetailsVM(navController).also {
                    instance = it
                }
            }
        }
        private var instanceAsFragmentallyScopedVM : FragmentallyScopedVM?
            get() = instance
            set(value) { instance = if (value==null) null else {value as TenantDetailsVM} }
    }

    val documentsRepo = DocumentsRepo()

    val tenant by lazy { MediatorLiveData<Tenant>() }
    val documents by lazy { MediatorLiveData<List<Document>>() }

    init {
        logz("TenantDetailsVM`Init`Open")
        documents.addSource(documentsRepo.streamGetDocumentsResponse) {
            documents.value = it
        }
        documents.addSource(tenant) {
            documentsRepo.getDocuments(it.id)
        }
        documents.addSource(documentsRepo.removeDocumentResult) {
            documentsRepo.getDocuments(it.document.tenantID)
        }
        documents.addSource(documentsRepo.updateDocumentResult) {
            if (it is UpdateDocumentResult.Success)
                documentsRepo.getDocuments(it.document.tenantID)
        }
        documents.addSource(documentsRepo.addDocumentResult) {
            if (it is AddDocumentResult.Success)
                documentsRepo.getDocuments(it.document.tenantID)
        }
    }

    fun finalize() {
        logz("TenantDetailsVM`finalize")
    }
}