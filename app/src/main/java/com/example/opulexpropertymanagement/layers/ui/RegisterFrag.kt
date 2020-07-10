package com.example.opulexpropertymanagement.layers.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.layers.view_models.RegisterVM
import com.example.opulexpropertymanagement.databinding.FragRegisterBinding
import com.example.opulexpropertymanagement.layers.ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.models.streamable.RegisterResult
import com.example.opulexpropertymanagement.util.onlyNew
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.exhaustive


class RegisterFrag : OXFragment(isToolbarEnabled = false) {

    lateinit var mBinding: FragRegisterBinding
    val navController by lazy { findNavController() }
    val registerVM: RegisterVM by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_register, container, false)
        mBinding.lifecycleOwner = this
        mBinding.registerVM = registerVM
        setupObservers()
        setupClickListeners()
        return mBinding.root
    }

    private fun setupObservers() {
        registerVM.repo.streamTryRegisterResult.onlyNew(viewLifecycleOwner).observe(viewLifecycleOwner, Observer {
            when (it) {
                is RegisterResult.Success -> {
                    navController.navigate(R.id.fragProperties)
                }
                is RegisterResult.Failure -> {
                    val msg = when (it) {
                        RegisterResult.Failure.Unknown -> "Registration Failed"
                        RegisterResult.Failure.EmailWasNull -> "Email required"
                        RegisterResult.Failure.PasswordWasNull -> "Password required"
                        RegisterResult.Failure.UserTypeWasNull -> "Landlord or Tenant required"
                        RegisterResult.Failure.EmailAlreadyExists -> "Email already exists"
                    }.exhaustive
                    easyToast(msg, Toast.LENGTH_LONG)
                }
            }
        })
    }

    private fun setupClickListeners() {
        mBinding.btnRegisterSend.setOnClickListener {
            registerVM.tryRegister()
        }
        mBinding.textviewAlreadyRegisteredSignIn.setOnClickListener {
            navController.navigate(R.id.fragLogin)
        }
    }
}