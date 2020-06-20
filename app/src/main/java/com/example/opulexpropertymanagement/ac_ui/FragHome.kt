package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragHomeBinding
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.ab_view_models.GlobalVM

class FragHome : OXFragment() {
    lateinit var mBinding: FragHomeBinding
    val navController by lazy { this.findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.frag_home, container, false
        )
        mBinding.userVM = GlobalVM
        mBinding.lifecycleOwner = this
        mBinding.btnGoToProperties.setOnClickListener {
            navController.navigate(R.id.action_global_fragProperties)
        }
        mBinding.btnGoToLogin.setOnClickListener {
            navController.navigate(R.id.fragLogin)
        }
        mBinding.btnLogout.setOnClickListener {
            GlobalVM.logout()
        }
        mBinding.btnPrintSomething.setOnClickListener {
            GlobalVM.printSomething()
        }
        return mBinding.root
    }
}