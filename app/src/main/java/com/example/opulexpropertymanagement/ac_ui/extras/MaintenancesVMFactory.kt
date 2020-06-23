package com.example.opulexpropertymanagement.ac_ui.extras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.opulexpropertymanagement.ab_view_models.MaintenancesVM
import com.example.opulexpropertymanagement.models.Property

class MaintenancesVMFactory(val property: Property) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MaintenancesVM(property) as T
    }
}