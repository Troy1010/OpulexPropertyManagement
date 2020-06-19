package com.example.opulexpropertymanagement.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.tmcommonkotlin.InputValidation
import com.example.tmcommonkotlin.logz
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

fun <T> convertRXToLiveData (observable: Observable<T>): LiveData<TryLoginResult> {
    return LiveDataReactiveStreams.fromPublisher(Repo.streamTryLogin.toFlowable(BackpressureStrategy.DROP))
}

fun <T> PublishSubject<T>.toLiveData(): LiveData<TryLoginResult> {
    return convertRXToLiveData(this)
}

fun handleInputValidationResult(
    validationResult: InputValidation.Result,
    layout: TextInputLayout,
    bClearError: Boolean = false
): Boolean {
    if (bClearError) {
        layout.isErrorEnabled = false
        return false
    }
    return when (validationResult) {
        is InputValidation.Result.Error -> {
            logz("error.validationResult.msg: ${validationResult.msg}")
//            layout.setErrorTextAppearance(R.style.ErrorText)
//            layout.error = validationResult.msg
            layout.error = "SDGDSFSDF"
            true
        }
        is InputValidation.Result.Warning -> {
            logz("warning")
            layout.setErrorTextAppearance(R.style.WarningText)
            layout.error = validationResult.msg
            false
        }
        is InputValidation.Result.Success -> {
            logz("success")
            layout.editText?.setText(validationResult.correctedValue)
            layout.isErrorEnabled = false
            false
        }
    }
}