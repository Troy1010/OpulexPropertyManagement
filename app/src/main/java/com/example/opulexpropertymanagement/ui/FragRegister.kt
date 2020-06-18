package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragRegisterBinding
import com.example.opulexpropertymanagement.ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.view_models.UserVM
import com.example.tmcommonkotlin.easyToast


class FragRegister : OXFragment(isToolbarEnabled = false) {

    lateinit var mBinding: FragRegisterBinding
    val navController by lazy {this.findNavController()}
    val userVM: UserVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_register, container, false)
        onCreateViewInit()
        userVM.registrationAttempt.observe(viewLifecycleOwner, Observer {
            if (!it) {
                navController.navigate(R.id.fragHome)
            } else {
                easyToast(requireActivity(), "Registration Failed")
            }
        })
        return mBinding.root
    }

    private fun onCreateViewInit() {
        mBinding.btnRegisterSend.setOnClickListener {
            val email = mBinding.textinputeditEmail.text.toString()
            val password = mBinding.textinputeditPassword.text.toString()
            userVM.register(email, password)
        }
        mBinding.textviewAlreadyRegisteredSignIn.setOnClickListener {
            navController.navigate(R.id.fragLogin)
        }
    }
}