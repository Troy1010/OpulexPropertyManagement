package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragHomeBinding

class FragHome : Fragment(R.layout.frag_home) {
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
        mBinding.lifecycleOwner = this
        val directions = FragHomeDirections.actionFragHomeToFragProperties()
        navController.navigate(directions)
        return mBinding.root
    }
}