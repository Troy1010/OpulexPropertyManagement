package com.example.opulexpropertymanagement.layers.ui.extras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.opulexpropertymanagement.layers.view_models.MaintenancesVM
import com.example.opulexpropertymanagement.models.Property

class MaintenancesVMFactory(val property: Property) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MaintenancesVM(property) as T
    }
}