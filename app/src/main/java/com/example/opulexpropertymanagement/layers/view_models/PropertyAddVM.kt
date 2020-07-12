package com.example.opulexpropertymanagement.layers.view_models

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.models.view_model_intermediates.InputValidationState
import com.example.opulexpropertymanagement.util.getDrawableUri

class PropertyAddVM(defaultImageUri : Uri?): ViewModel() {
    val addressInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val cityInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val countryInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val mortgageInfoInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val priceInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val stateInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }

    val image by lazy { MutableLiveData<Uri>().apply { value = defaultImageUri } }
}