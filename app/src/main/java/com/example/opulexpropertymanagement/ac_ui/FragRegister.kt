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
import com.example.opulexpropertymanagement.ab_view_models.RegisterVM
import com.example.opulexpropertymanagement.databinding.FragRegisterBinding
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.ab_view_models.UserVM
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.StreamableTryLogin
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logv


class FragRegister : OXFragment(isToolbarEnabled = false) {

    lateinit var mBinding: FragRegisterBinding
    val navController by lazy {this.findNavController()}
    val userVM: UserVM by activityViewModels()
    val registerVM: RegisterVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_register, container, false)
        onCreateViewInit()
        registerVM.registrationAttempt.observe(viewLifecycleOwner, Observer {
            if (it is StreamableTryLogin.Success) {
                navController.navigate(R.id.fragHome)
                userVM.user.value = it.user
            } else if (it is StreamableTryLogin.Failure) {
                logv("RegistrationFailed`${it.msg}")
                easyToast(requireActivity(), "Registration Failed")
                userVM.user.value = null
            }
        })
        return mBinding.root
    }

    private fun onCreateViewInit() {
        mBinding.btnRegisterSend.setOnClickListener {
            val email = mBinding.textinputeditEmail.text.toString()
            val password = mBinding.textinputeditPassword.text.toString()
            registerVM.register(email, password, userVM.userType.value?:UserType.Landlord)
        }
        mBinding.textviewAlreadyRegisteredSignIn.setOnClickListener {
            navController.navigate(R.id.fragLogin)
        }
    }
}