package com.example.opulexpropertymanagement.aa.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.aa.view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.aa.ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.models.streamable.RemoveDocumentResult
import com.example.opulexpropertymanagement.models.streamable.UpdateDocumentResult
import com.example.opulexpropertymanagement.util.JavaUtil
import com.example.opulexpropertymanagement.util.easyPicasso
import com.example.opulexpropertymanagement.util.onlyNew
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_document_details.*



class DocumentDetailsFrag: OXFragment() {
    val args by lazy { arguments?.let { DocumentDetailsFragArgs.fromBundle(it) } }
    val document by lazy { args?.document!! }
    val tenantDetailsVM: TenantDetailsVM by lazy { TenantDetailsVM.getInstance(navController) }
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
        tenantDetailsVM.documentsRepo.updateDocumentResult.onlyNew(viewLifecycleOwner).observe(viewLifecycleOwner, Observer {
            if (it is UpdateDocumentResult.Success) {
                textview_document_title.text = it.document.title
            } else {
                logz("Failed to update document:$it")
                easyToast(requireActivity(), "Failed to update document")
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
        btn_save_text.setOnClickListener {
            val newTitle = edittext_document_title.text.toString()
            val updatedDocument = Document(document.id, document.tenantID, newTitle)
            tenantDetailsVM.documentsRepo.updateDocument(updatedDocument)
            btn_save_text.visibility = View.GONE
            edittext_document_title.visibility = View.GONE
            textview_document_title.visibility = View.VISIBLE
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v==textview_document_title) {
            activity?.menuInflater?.inflate(R.menu.text_menu, menu)
        }
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.copy_text -> {
                JavaUtil.copyIt(requireActivity(), textview_document_title.text.toString())
            }
            R.id.edit_text -> {
                edittext_document_title.setText(textview_document_title.text)
                edittext_document_title.visibility = View.VISIBLE
                btn_save_text.visibility = View.VISIBLE
                textview_document_title.visibility = View.GONE
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        setupView()
        setupClickListeners()
    }

    private fun setupView() {
        imageview_document.easyPicasso(document.imageUrlTask)
        textview_document_title.text = document.title
        registerForContextMenu(textview_document_title)
    }
}