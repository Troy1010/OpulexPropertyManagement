package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.LoginVM
import com.example.opulexpropertymanagement.ab_view_models.PropertyAddVM
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragPropertyAddBinding
import com.example.opulexpropertymanagement.databinding.IncludibleTextInputBinding
import com.example.opulexpropertymanagement.models.view_model_intermediates.InputValidationState
import com.example.opulexpropertymanagement.util.handleInputValidationResult
import com.example.tmcommonkotlin.InputValidation
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_property_add.*
import kotlinx.android.synthetic.main.includible_text_input.view.*

class FragPropertyAdd: OXFragment() {
    lateinit var mBinding: FragPropertyAddBinding
    val propertyAddVM: PropertyAddVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_property_add, container, false)
        mBinding.lifecycleOwner = this
        mBinding.propertyAddVM = propertyAddVM
        setupListeners()
        return mBinding.root
    }


    private fun setupListeners() {
        mBinding.textinputAddress.textinput.setOnFocusChangeListener { _, hasFocus ->
            val inputValidationState = propertyAddVM.addressInputValidationState.value!!
            handleValidationResult(inputValidationState, InputValidation.asStreetAddress, hasFocus)
        }
        mBinding.textinputCity.textinput.setOnFocusChangeListener { _, hasFocus ->
            val inputValidationState = propertyAddVM.cityInputValidationState.value!!
            handleValidationResult(inputValidationState, InputValidation.asCity, hasFocus)
        }
        mBinding.textinputCountry.textinput.setOnFocusChangeListener { _, hasFocus ->
            val inputValidationState = propertyAddVM.countryInputValidationState.value!!
            handleValidationResult(inputValidationState, InputValidation.asRequired, hasFocus)
        }
        mBinding.textinputMortgageInfo.textinput.setOnFocusChangeListener { _, hasFocus ->
            val inputValidationState = propertyAddVM.mortgageInfoInputValidationState.value!!
            handleValidationResult(inputValidationState, InputValidation.asRequired, hasFocus)
        }
        mBinding.textinputPrice.textinput.setOnFocusChangeListener { _, hasFocus ->
            val inputValidationState = propertyAddVM.priceInputValidationState.value!!
            handleValidationResult(inputValidationState, InputValidation.asMoney, hasFocus)
        }
        mBinding.textinputState.textinput.setOnFocusChangeListener { _, hasFocus ->
            val inputValidationState = propertyAddVM.stateInputValidationState.value!!
            handleValidationResult(inputValidationState, InputValidation.asState, hasFocus)
        }
    }

    fun handleValidationResult(inputValidationState: InputValidationState, validationLambda:(String)->InputValidation.Result, bSkip: Boolean) {
        if (bSkip) {
            inputValidationState.isErrorEnabled.value = false
        } else {
            val validationResult = validationLambda(inputValidationState.text.value?:"")
            logz("validating:${inputValidationState.text.value?:""} validationResult:$validationResult")
            if (validationResult is InputValidation.Result.Success) {
                inputValidationState.isErrorEnabled.value = false
                inputValidationState.errorMsg.value = ""
                inputValidationState.text.value = validationResult.correctedValue
            } else if (validationResult is InputValidation.Result.Error) {
                inputValidationState.isErrorEnabled.value = true
                inputValidationState.errorMsg.value = validationResult.msg
                inputValidationState.textAppearance.value = R.style.ErrorText
            }
        }
    }


}