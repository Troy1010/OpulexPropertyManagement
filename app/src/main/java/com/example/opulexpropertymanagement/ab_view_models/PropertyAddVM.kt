package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.ab_view_models.inheritables.IJobStopper
import com.example.opulexpropertymanagement.ab_view_models.inheritables.JobStopper
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.view_model_intermediates.InputValidationState
import com.example.tmcommonkotlin.Coroutines

class PropertyAddVM: ViewModel(), IJobStopper by JobStopper() {
    val addressInputValidationState = MutableLiveData<InputValidationState>()
    val cityInputValidationState = MutableLiveData<InputValidationState>()
    val countryInputValidationState = MutableLiveData<InputValidationState>()
    val mortgageInfoInputValidationState = MutableLiveData<InputValidationState>()
    val priceInputValidationState = MutableLiveData<InputValidationState>()
    val stateInputValidationState = MutableLiveData<InputValidationState>()
}