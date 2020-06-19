package com.example.opulexpropertymanagement.ab_view_models

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout




@BindingAdapter("app:errorText")
fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
    view.error = errorMessage
}