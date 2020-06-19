package com.example.opulexpropertymanagement.models.view_model_intermediates

import androidx.lifecycle.MutableLiveData

class InputValidationState {
    val text = MutableLiveData<String>()
    val isErrorEnabled = MutableLiveData<Boolean>()
    val errorMsg = MutableLiveData<String>()
    val textAppearance = MutableLiveData<Int>()
    fun clearError() {
        isErrorEnabled.value = false
    }
}