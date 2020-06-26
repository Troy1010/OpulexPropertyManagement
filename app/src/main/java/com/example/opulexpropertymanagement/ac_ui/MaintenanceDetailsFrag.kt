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
import com.example.opulexpropertymanagement.ab_view_models.MaintenanceAddVM
import com.example.opulexpropertymanagement.ab_view_models.MaintenancesVM
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragMaintenanceDetailsBinding
import com.example.opulexpropertymanagement.models.Maintenance
import com.example.tmcommonkotlin.logz

class MaintenanceDetailsFrag : OXFragment() {
//    val maintenancesVMStoreOwner by lazy { parentFragment }
    lateinit var fragBinding: FragMaintenanceDetailsBinding
    val maintenancesVM: MaintenancesVM by viewModels({ PropertyDetailsStoreOwner!! })
    val maintenanceAddVM: MaintenanceAddVM by viewModels()
    val navController by lazy { findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        for (x in viewModelStore) {
//
//        }
//        logz("viewModelStore:${viewModelStore.}")
        fragBinding = DataBindingUtil.inflate(
            inflater, R.layout.frag_maintenance_details, container, false
        )
        fragBinding.lifecycleOwner = PropertyDetailsLifecycleOwner
        setupObservers()
        return fragBinding.root
    }

    private fun setupObservers() {
    }

    override fun onStart() {
        super.onStart()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        fragBinding.btnMaintenanceDone.setOnClickListener {
            // sync
            //  *TODO link this directly
            val id = maintenancesVM.selectedMaintenance.value?.id
            if (id!=null) {
                val maintenance = Maintenance(
                    description = fragBinding.edittextMaintenanceDescription.text.toString(),
                    id = id
                )
                maintenancesVM.repo.updateMaintenance(maintenancesVM.propertyID, maintenance)
            }
            //
            navController.navigateUp()
        }
    }
}