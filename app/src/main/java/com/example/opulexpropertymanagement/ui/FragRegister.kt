package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragRegisterBinding
import com.example.opulexpropertymanagement.ui.inheritables.OXFragment


class FragRegister : OXFragment() {

    lateinit var mBinding: FragRegisterBinding
    val navController by lazy {this.findNavController()}
    val userVM: UserVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_register, container, false)
        init()
        return mBinding.root
    }

    private fun init() {
        mBinding.btnRegisterSend.setOnClickListener {
            navController.navigate(R.id.fragHome)
        }
        mBinding.textviewNewUserClickHere.setOnClickListener {
            navController.navigate(R.id.fragLogin)
        }
    }
}