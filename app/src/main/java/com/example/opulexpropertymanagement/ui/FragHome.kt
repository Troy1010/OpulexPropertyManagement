package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragHomeBinding
import com.example.opulexpropertymanagement.ui.inheritables.OXFragment

class FragHome : OXFragment() {
    lateinit var mBinding: FragHomeBinding
    val navController by lazy { this.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userVM: UserVM by viewModels()
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.frag_home, container, false
        )
        mBinding.lifecycleOwner = this
        mBinding.btnGoToProperties.setOnClickListener {
//            val directions = FragHomeDirections.actionFragHomeToActivitySplashscreen()
//            navController.navigate(directions)
            val directions = FragHomeDirections.actionFragHomeToFragProperties()
            navController.navigate(directions)
        }
        return mBinding.root
    }
}