package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.MaintenanceAddVM
import com.example.opulexpropertymanagement.ab_view_models.MaintenancesVM
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragMaintenanceDetailsBinding
import com.example.opulexpropertymanagement.models.Maintenance

class MaintenanceDetailsFrag : OXFragment() {
    lateinit var fragBinding: FragMaintenanceDetailsBinding
    val maintenancesVM: MaintenancesVM by viewModels({ PropertyDetailsStoreOwner!! })
    val maintenanceAddVM: MaintenanceAddVM by viewModels()
    val navController by lazy { findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragBinding = DataBindingUtil.inflate(
            inflater, R.layout.frag_maintenance_details, container, false
        )
        fragBinding.lifecycleOwner = this
        return fragBinding.root
    }

    override fun onStart() {
        super.onStart()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        fragBinding.btnMaintenanceDone.setOnClickListener {
            // sync
            val maintenance = maintenanceAddVM.maintenance.value
            if (maintenance!=null)
                maintenancesVM.repo.addMaintenance(maintenancesVM.propertyID, maintenance)
            //
            navController.navigateUp()
        }
    }
}