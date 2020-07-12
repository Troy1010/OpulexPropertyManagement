package com.example.opulexpropertymanagement.layers.view_models

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.layers.data_layer.TenantsRepo
import com.example.opulexpropertymanagement.models.view_model_intermediates.InputValidationState
import com.example.opulexpropertymanagement.util.getDrawableUri

class TenantAddVM(defaultImageUri: Uri?): ViewModel() {
    val nameInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val emailInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }
    val phoneInputValidationState by lazy { MutableLiveData<InputValidationState>().apply { value = InputValidationState() } }

    val image by lazy { MutableLiveData<Uri>().apply { value = defaultImageUri } }

    val repo = TenantsRepo
}