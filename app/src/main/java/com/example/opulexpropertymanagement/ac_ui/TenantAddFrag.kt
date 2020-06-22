package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.PropertiesVM
import com.example.opulexpropertymanagement.ab_view_models.PropertyAddVM
import com.example.opulexpropertymanagement.ab_view_models.TenantAddVM
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragTenantAddBinding

class TenantAddFrag: OXFragment() {
    lateinit var mBinding: FragTenantAddBinding
    val tenantAddVM: TenantAddVM by viewModels()
    val navController by lazy { this.findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_tenant_add, container, false)
        mBinding.lifecycleOwner = this
        mBinding.tenantAddVM = tenantAddVM
        return mBinding.root
    }
}