package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragRegisterBinding
import com.example.opulexpropertymanagement.ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.view_models.UserVM
import com.example.tmcommonkotlin.logz
import kotlinx.coroutines.*


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
        init()
        return mBinding.root
    }

    private fun init() {
        mBinding.btnRegisterSend.setOnClickListener {
//            navController.navigate(R.id.fragHome)
            val email = mBinding.textinputeditEmail.text.toString()
            val password = mBinding.textinputeditPassword.text.toString()
            myJob = CoroutineScope(Dispatchers.IO).launch {
                val result = Repo.register(email, password)
                withContext(Dispatchers.Main) {
                    logz ("result:${result.string()}")
                }
            }
        }
        mBinding.textviewAlreadyRegisteredSignIn.setOnClickListener {
            navController.navigate(R.id.fragLogin)
        }
    }


    private var myJob: Job? = null
    override fun onDestroy() {
        myJob?.cancel()
        super.onDestroy()
    }
}