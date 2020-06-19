package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragPropertyAddBinding
import com.example.opulexpropertymanagement.databinding.IncludibleTextInputBinding
import com.example.opulexpropertymanagement.util.handleInputValidationResult
import com.example.tmcommonkotlin.InputValidation
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_property_add.*
import kotlinx.android.synthetic.main.includible_text_input.view.*

class FragPropertyAdd: OXFragment(), View.OnFocusChangeListener {
    lateinit var mBinding: FragPropertyAddBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_property_add, container, false)
        setupListeners()
        return mBinding.root
    }


    private fun setupListeners() {
        mBinding.textinputAddress.textinput.setOnFocusChangeListener(this)
        mBinding.textinputCity.textinput.setOnFocusChangeListener(this)
        mBinding.textinputCountry.textinput.setOnFocusChangeListener(this)
        mBinding.textinputMortgageInfo.textinput.setOnFocusChangeListener(this)
        mBinding.textinputPrice.textinput.setOnFocusChangeListener(this)
        mBinding.textinputState.textinput.setOnFocusChangeListener(this)
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        logz("v.id:${v.id}")
        logz("mBinding.textinputAddress.textinput.id:${mBinding.textinputAddress.textinput.id}")
        when (v.id) {
            mBinding.textinputAddress.textinput.id -> handleInputValidationResult(
                InputValidation.asStreetAddress(mBinding.textinputAddress.textinput.text.toString()),
                mBinding.textinputAddress.textinputlayout, hasFocus
            )
            mBinding.textinputMortgageInfo.textinput.id -> handleInputValidationResult(
                InputValidation.asStreetAddress(mBinding.textinputMortgageInfo.textinput.text.toString()),
                mBinding.textinputMortgageInfo.textinputlayout, hasFocus
            )
            mBinding.textinputCity.textinput.id -> handleInputValidationResult(
                InputValidation.asStreetAddress(mBinding.textinputCity.textinput.text.toString()),
                mBinding.textinputCity.textinputlayout, hasFocus
            )
            mBinding.textinputCountry.textinput.id -> handleInputValidationResult(
                InputValidation.asStreetAddress(mBinding.textinputCountry.textinput.text.toString()),
                mBinding.textinputCountry.textinputlayout, hasFocus
            )
            mBinding.textinputPrice.textinput.id -> handleInputValidationResult(
                InputValidation.asStreetAddress(mBinding.textinputPrice.textinput.text.toString()),
                mBinding.textinputPrice.textinputlayout, hasFocus
            )
            mBinding.textinputState.textinput.id -> handleInputValidationResult(
                InputValidation.asStreetAddress(mBinding.textinputState.textinput.text.toString()),
                mBinding.textinputState.textinputlayout, hasFocus
            )
        }
    }


}