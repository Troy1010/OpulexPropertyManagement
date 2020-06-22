package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.util.easyPicasso
import kotlinx.android.synthetic.main.frag_document_details.*

class DocumentDetailsFrag: OXFragment() {
    val args by lazy { arguments?.let { DocumentDetailsFragArgs.fromBundle(it) } }
    val document by lazy { args?.document!! }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.frag_document_details, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        setupView()
    }

    private fun setupView() {
        imageview_document.easyPicasso(document.imageUrlTask)
        textview_document_title.text = document.title
    }
}