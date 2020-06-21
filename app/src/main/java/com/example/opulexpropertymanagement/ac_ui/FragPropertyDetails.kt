package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.GlobalVM
import com.example.opulexpropertymanagement.ab_view_models.PropertiesVM
import com.example.opulexpropertymanagement.ab_view_models.PropertyAddVM
import com.example.opulexpropertymanagement.ab_view_models.PropertyDetailsVM
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragPropertyDetailsBinding
import com.example.opulexpropertymanagement.models.ReasonForLogin


class FragPropertyDetails: OXFragment() {
    lateinit var mBinding: FragPropertyDetailsBinding
    val propertyDetailsVM: PropertyDetailsVM by viewModels()
    val navController by lazy { this.findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_property_details, container, false)
        mBinding.lifecycleOwner = this
        mBinding.propertyDetailsVM = propertyDetailsVM
        setupClickListeners()
        setupObservers()
        return mBinding.root
    }

    private fun setupObservers() {

    }

    private fun setupClickListeners() {

    }
}