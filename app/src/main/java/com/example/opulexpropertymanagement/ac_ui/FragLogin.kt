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
import com.example.opulexpropertymanagement.databinding.FragLoginBinding
import com.example.opulexpropertymanagement.models.ReasonForLogin
import com.example.opulexpropertymanagement.models.streamable.StreamableTryLogin
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.ab_view_models.LoginVM
import com.example.opulexpropertymanagement.ab_view_models.UserVM
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logv
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.frag_login.*
import kotlinx.coroutines.Job


class FragLogin : OXFragment(isToolbarEnabled = false) {

    lateinit var mBinding: FragLoginBinding
    val navController by lazy { this.findNavController() }
    val userVM: UserVM by activityViewModels()
    val loginVM: LoginVM by viewModels()
    val compositeDisposable by lazy { CompositeDisposable() }
    val args by lazy { arguments?.let { FragLoginArgs.fromBundle(it) } }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_login, container, false)
        mBinding.textviewNewUserClickHere.setOnClickListener {
            navController.navigate(R.id.fragRegister)
        }
        mBinding.btnLoginSend.setOnClickListener {
            val email = mBinding.textinputeditEmail.text.toString()
            val password = mBinding.textinputeditPassword.text.toString()
            loginVM.tryLogin(email, password)
        }
        loginVM.liveDataTryLogin.observe(viewLifecycleOwner, Observer {
            if (it is StreamableTryLogin.Success) {
                if (args?.ReasonForLoginInt == ReasonForLogin.Properties.ordinal) {
                    navController.navigate(R.id.fragProperties)
                } else {
                    navController.navigate(R.id.fragHome)
                }
                userVM.user.value = it.user
            } else if (it is StreamableTryLogin.Failure) {
                logv("Login Failed:${it.msg}")
                easyToast(requireActivity(), "Login Failed")
                userVM.user.value = null
            }
        })
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        // Does this survive rotation..?
        if (args?.ReasonForLoginInt == ReasonForLogin.Properties.ordinal) {
            textview_reason_for_login.text =
                "In order to see your properties, you must be logged in."
        }
    }
    private var myJob: Job? = null
    override fun onDestroy() {
        myJob?.cancel()
        compositeDisposable.dispose()
        super.onDestroy()
    }
}