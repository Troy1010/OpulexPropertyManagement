package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragLoginBinding
import com.example.opulexpropertymanagement.models.ReasonForLogin
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.ab_view_models.LoginVM
import com.example.opulexpropertymanagement.ab_view_models.GlobalVM
import com.example.opulexpropertymanagement.util.onlyNew
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logv
import com.example.tmcommonkotlin.logz
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.frag_login.*
import kotlinx.coroutines.Job


class FragLogin : OXFragment(isToolbarEnabled = false) {

    lateinit var mBinding: FragLoginBinding
    val navController by lazy { this.findNavController() }
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
            loginVM.repo.tryLogin(email, password)
            logz("loginSend clicked")
        }
        setupObserver()
        return mBinding.root
    }

    private fun setupObserver() {
        loginVM.repo.liveDataTryLogin.onlyNew(viewLifecycleOwner).observe(viewLifecycleOwner, Observer {
            logz("TryLoginResult observed.")
            if (it is TryLoginResult.Success) {
                if (args?.ReasonForLoginInt == ReasonForLogin.TriedToAddProperty.ordinal) {
                    navController.navigateUp()
                } else {
                    navController.navigate(R.id.fragHome)
                }
            } else if (it is TryLoginResult.Failure) {
                logv("Login Failed:${it.msg}")
                easyToast(requireActivity(), "Login Failed")
            }
        })
    }


    override fun onStart() {
        super.onStart()
        // Does this survive rotation..?
        if (args?.ReasonForLoginInt == ReasonForLogin.Properties.ordinal) {
            textview_reason_for_login.text =
                "In order to see your properties, you must be logged in."
        } else if (args?.ReasonForLoginInt == ReasonForLogin.TriedToAddProperty.ordinal) {
            textview_reason_for_login.text =
                "In order to add a property, you must be logged in."
        }
    }
    private var myJob: Job? = null
    override fun onDestroy() {
        myJob?.cancel()
        compositeDisposable.dispose()
        super.onDestroy()
    }
}