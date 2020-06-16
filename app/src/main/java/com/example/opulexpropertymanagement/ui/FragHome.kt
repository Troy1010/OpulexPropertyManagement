package com.example.pg_mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.grocerygo.activities_and_frags.Inheritables.TMFragment
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragHomeBinding
import com.example.pg_mvvm.databinding.FragHomeBinding
import com.example.pg_mvvm.view_models.UserVM
import com.example.tmcommonkotlin.logz
import io.reactivex.disposables.Disposable

class FragHome : Fragment(R.layout.frag_home) {
    lateinit var mBinding: FragHomeBinding
    val userVM: UserVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.frag_home, container, false
        )
        mBinding.userVM = userVM
        mBinding.lifecycleOwner = this
        return mBinding.root
    }
}