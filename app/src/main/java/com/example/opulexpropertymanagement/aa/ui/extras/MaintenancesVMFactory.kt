package com.example.opulexpropertymanagement.aa.ui.extras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.opulexpropertymanagement.aa.view_models.MaintenancesVM
import com.example.opulexpropertymanagement.models.Property

class MaintenancesVMFactory(val property: Property) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MaintenancesVM(property) as T
    }
}