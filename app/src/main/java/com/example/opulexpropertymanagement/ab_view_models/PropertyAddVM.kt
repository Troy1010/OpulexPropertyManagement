package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.models.view_model_intermediates.InputValidationState

class PropertyAddVM: ViewModel() {
    val addressInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val cityInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val countryInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val mortgageInfoInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val priceInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val stateInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
}