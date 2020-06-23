package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.models.Maintenance
import com.example.opulexpropertymanagement.models.view_model_intermediates.InputValidationState

class MaintenanceAddVM: ViewModel() {
    val descriptionInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val maintenance by lazy { MutableLiveData<Maintenance>() }
}