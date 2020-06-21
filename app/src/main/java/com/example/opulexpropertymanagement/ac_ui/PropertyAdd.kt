package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.PropertiesVM
import com.example.opulexpropertymanagement.ab_view_models.PropertyAddVM
import com.example.opulexpropertymanagement.ab_view_models.GlobalVM
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragPropertyAddBinding
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.PropertyStatus
import com.example.opulexpropertymanagement.models.ReasonForLogin
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.opulexpropertymanagement.models.view_model_intermediates.InputValidationState
import com.example.opulexpropertymanagement.util.onlyNew
import com.example.tmcommonkotlin.InputValidation
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logz

class PropertyAdd : OXFragment() {
    lateinit var mBinding: FragPropertyAddBinding
    val propertyAddVM: PropertyAddVM by viewModels()
    val propertiesVM: PropertiesVM by viewModels()
    val navController by lazy { this.findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_property_add, container, false)
        mBinding.lifecycleOwner = this
        mBinding.propertyAddVM = propertyAddVM
        setupOnFocusChangedListeners()
        setupClickListeners()
        setupObservers()
        GlobalVM.user.observe(viewLifecycleOwner, Observer { user ->
            if (user==null) {
                val directions = PropertyAddDirections.actionFragPropertyAddToFragLogin(ReasonForLoginInt = ReasonForLogin.TriedToAddProperty.ordinal)
                navController.navigate(directions)
            }
        })
        return mBinding.root
    }

    private fun setupObservers() {
        propertiesVM.repo.streamAddPropertyResult.onlyNew(viewLifecycleOwner).observe(viewLifecycleOwner, Observer {
            if (it is AddPropertyResult.Success) {
                navController.navigateUp()
            } else {
                logz("AddPropertyFailed`${it}")
                easyToast(requireActivity(), "Add Property Failed")
            }
        })
    }

    private fun setupClickListeners() {
        mBinding.btnAdd.setOnClickListener {
            val user = GlobalVM.user.value
            if (user != null) {
                propertiesVM.repo.addProperty(Property(
                    streetAddress = mBinding.textinputAddress.textinput.text.toString(),
                    city = mBinding.textinputCity.textinput.text.toString(),
                    country = mBinding.textinputCountry.textinput.text.toString(),
                    mortgageInfo = mBinding.textinputMortgageInfo.textinput.text.toString(),
                    state = mBinding.textinputState.textinput.text.toString(),
                    purchasePrice = mBinding.textinputPrice.textinput.text.toString(),
                    status = PropertyStatus.Unavailable.name
                ), user)} else {
                easyToast(requireActivity(), "Login required")
            }
        }
    }


    private fun setupOnFocusChangedListeners() {
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

    fun handleValidationResult(
        inputValidationState: InputValidationState,
        validationLambda: (String) -> InputValidation.Result,
        bSkip: Boolean
    ) {
        if (bSkip) {
            inputValidationState.isErrorEnabled.value = false
        } else {
            val validationResult = validationLambda(inputValidationState.text.value ?: "")
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