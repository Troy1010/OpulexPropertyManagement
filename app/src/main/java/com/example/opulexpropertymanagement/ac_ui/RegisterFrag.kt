package com.example.opulexpropertymanagement.ac_ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.RegisterVM
import com.example.opulexpropertymanagement.databinding.FragRegisterBinding
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.ab_view_models.GlobalVM
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.RegisterResult
import com.example.opulexpropertymanagement.util.onlyNew
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logv
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_home.*


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
        logz("style:${mBinding.switchUserType.explicitStyle}")
        return mBinding.root
    }

    private fun setupObservers() {
        registerVM.repo.streamTryRegisterResult.onlyNew(viewLifecycleOwner).observe(viewLifecycleOwner, Observer {
            when (it) {
                is RegisterResult.Success -> {
                    navController.navigate(R.id.fragProperties)
                }
                is RegisterResult.Failure -> {
                    logv("RegistrationFailed`${it.msg}")
                    if (it is RegisterResult.Failure.EmailAlreadyExists) {
                        easyToast(requireActivity(), "Email already exists")
                    } else {
                        easyToast(requireActivity(), "Registration Failed")
                    }
                }
            }
        })
//        registerVM.isChecked.observe(viewLifecycleOwner, Observer {
//            switch_1.trackTintList = ColorStateList(
//
//            )
//        })
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