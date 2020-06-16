package com.example.pg_mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pg_mvvm.databinding.FragRegisterBinding
import com.example.pg_mvvm.view_models.UserVM

class FragRegister : Fragment() {

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