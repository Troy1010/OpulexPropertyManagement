package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragHomeBinding
import com.example.opulexpropertymanagement.ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.view_models.UserVM
import com.example.tmcommonkotlin.logz

class FragHome : OXFragment() {
    lateinit var mBinding: FragHomeBinding
    val navController by lazy { this.findNavController() }
    val userVM: UserVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.frag_home, container, false
        )
        logz("FragHome`onCreateView`userVM:${userVM}")
        logz("FragHome`onCreateView`userVM.email:${userVM.user.value?.email}")
        mBinding.userVM = userVM
        mBinding.lifecycleOwner = this
        mBinding.btnGoToProperties.setOnClickListener {
//            val directions = FragHomeDirections.actionFragHomeToActivitySplashscreen()
//            navController.navigate(directions)
            val directions = FragHomeDirections.actionFragHomeToFragProperties()
            navController.navigate(directions)
        }
        mBinding.btnGoToLogin.setOnClickListener {
            navController.navigate(R.id.fragLogin)
        }
        mBinding.btnLogout.setOnClickListener {
            userVM.logout()
        }
        mBinding.btnPrintSomething.setOnClickListener {
            userVM.printSomething()
        }
        return mBinding.root
    }
}