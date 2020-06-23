package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.MaintenanceAddVM
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragAddMaintenanceBinding

class MaintenanceAddFrag : OXFragment() {
    lateinit var fragBinding: FragAddMaintenanceBinding
    val maintenanceAddVM: MaintenanceAddVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragBinding = DataBindingUtil.inflate(
            inflater, R.layout.frag_add_maintenance, container, false
        )
        fragBinding.lifecycleOwner = this
        fragBinding.maintenance = maintenanceAddVM.maintenance.value // is this okay?
        return fragBinding.root
    }
}