package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment

class TenantDetailsFrag: OXFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_tenant_details, container, false)
    }
}