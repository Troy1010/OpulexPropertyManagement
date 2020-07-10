package com.example.opulexpropertymanagement.layers.z_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.layers.view_models.GlobalVM
import com.example.opulexpropertymanagement.layers.z_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.app.fbUserDBTable
import com.example.opulexpropertymanagement.databinding.FragHomeBinding
import com.example.tmcommonkotlin.logz
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class HomeFrag : OXFragment() {
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
            fbUserDBTable?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                logz("onCancelled")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                logz("onDataChange")
                for (data in dataSnapshot.children) {
                    logz(data.toString())
                }
            }
        })
        }
        mBinding.btnDoSomethingTwo.setOnClickListener {
            logz("DoSomethingTwo")
            fbUserDBTable?.child("PropertyPictures")?.setValue(arrayListOf(1,2,3,4))

        }
        return mBinding.root
    }
}