package com.example.opulexpropertymanagement.ac_ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.aa_repo.DocumentsRepo
import com.example.opulexpropertymanagement.ab_view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.models.streamable.RemoveDocumentResult
import com.example.opulexpropertymanagement.util.easyPicasso
import com.example.opulexpropertymanagement.util.onlyNew
import com.example.tmcommonkotlin.easyToast
import kotlinx.android.synthetic.main.frag_document_details.*

class DocumentDetailsFrag: OXFragment() {
    val args by lazy { arguments?.let { DocumentDetailsFragArgs.fromBundle(it) } }
    val document by lazy { args?.document!! }
    val tenantDetailsVM: TenantDetailsVM by viewModels({TenantDetailsStoreOwner!!})
    val navController by lazy { this.findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.frag_document_details, container, false)
        setupObservers()
        return v
    }

    private fun setupObservers() {
        tenantDetailsVM.documentsRepo.removeDocumentResult.onlyNew(viewLifecycleOwner).observe(viewLifecycleOwner, Observer {
            if (it is RemoveDocumentResult.Success) {
                navController.navigateUp()
            } else {
                easyToast(requireActivity(), "Failed to delete document")
            }
        })
    }

    private fun setupClickListeners() {
        btn_delete_document.setOnClickListener {
            AlertDialog.Builder(activity)
                .setTitle("Delete Document")
                .setMessage("Are you sure you want to delete this document?\nThis action cannot be undone.")
                .setPositiveButton("Delete") { _, _ ->
                    tenantDetailsVM.documentsRepo.removeDocument(document)
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .create().show()
        }
    }

    override fun onStart() {
        super.onStart()
        setupView()
        setupClickListeners()
    }

    private fun setupView() {
        imageview_document.easyPicasso(document.imageUrlTask)
        textview_document_title.text = document.title
    }
}