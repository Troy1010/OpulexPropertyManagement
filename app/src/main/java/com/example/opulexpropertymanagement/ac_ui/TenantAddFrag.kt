package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.GlobalVM
import com.example.opulexpropertymanagement.ab_view_models.TenantAddVM
import com.example.opulexpropertymanagement.ac_ui.extras.BottomDialogForPhoto
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragTenantAddBinding
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.models.streamable.AddTenantResult
import com.example.opulexpropertymanagement.models.view_model_intermediates.InputValidationState
import com.example.tmcommonkotlin.InputValidation
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logz

class TenantAddFrag : OXFragment() {
    lateinit var mBinding: FragTenantAddBinding
    val tenantAddVM: TenantAddVM by viewModels()
    val args by lazy { arguments?.let { TenantAddFragArgs.fromBundle(it) } }
    val navController by lazy { this.findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_tenant_add, container, false)
        mBinding.lifecycleOwner = this
        mBinding.tenantAddVM = tenantAddVM
        setupOnFocusChangedListeners()
        setupClickListeners()
        setupObservers()
        return mBinding.root
    }

    private fun setupObservers() {
        tenantAddVM.repo.streamAddTenantResult.observe(viewLifecycleOwner, Observer {
            if (it is AddTenantResult.Success) {
                navController.navigateUp()
            } else {
                logz("AddPropertyFailed`${it}")
                easyToast(requireActivity(), "Add Property Failed")
            }
        })
    }

    private fun setupClickListeners() {
        mBinding.imageview1.setOnClickListener {
            val addPhotoBottomDialogFragment = BottomDialogForPhoto(requireActivity()) { uri, _ ->
                tenantAddVM.image.value = uri
            }
            addPhotoBottomDialogFragment.show(
                requireActivity().supportFragmentManager,
                "bottom_sheet"
            )
        }
        mBinding.btnAdd.setOnClickListener {
            val landlord = GlobalVM.user.value
            val image = tenantAddVM.image.value
            val property = args?.property
            if ((landlord != null) && (image != null) && (property != null)) {
                tenantAddVM.repo.addTenant(
                    Tenant(
                        email = mBinding.textinputEmail.textinput.text.toString(),
                        fullAddress = property.singleLineAddress,
                        landlordID = landlord.id,
                        name = mBinding.textinputName.textinput.text.toString(),
                        phone = mBinding.textinputPhone.textinput.text.toString(),
                        propertyid = property.id
                    ), image
                )
            } else if (landlord == null) {
                logz("Login required`landlord:$landlord")
                easyToast(requireActivity(), "Login required")
            } else if (image == null) {
                logz("Image required")
                easyToast(requireActivity(), "Image required")
            } else if (property == null) {
                logz("Add Tenant Failed`Property required:$property")
                easyToast(requireActivity(), "Add Tenant Failed")
            }
        }
    }


    private fun setupOnFocusChangedListeners() {
        mBinding.textinputName.textinput.setOnFocusChangeListener { _, hasFocus ->
            val inputValidationState = tenantAddVM.nameInputValidationState.value!!
            handleValidationResult(inputValidationState, InputValidation.asName, hasFocus)
        }
        mBinding.textinputEmail.textinput.setOnFocusChangeListener { _, hasFocus ->
            val inputValidationState = tenantAddVM.emailInputValidationState.value!!
            handleValidationResult(inputValidationState, InputValidation.asEmail, hasFocus)
        }
        mBinding.textinputPhone.textinput.setOnFocusChangeListener { _, hasFocus ->
            val inputValidationState = tenantAddVM.phoneInputValidationState.value!!
            handleValidationResult(inputValidationState, InputValidation.asPhone, hasFocus)
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