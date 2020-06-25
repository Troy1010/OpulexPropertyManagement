package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragLoginBinding
import com.example.opulexpropertymanagement.models.ReasonForLogin
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.ab_view_models.LoginVM
import com.example.opulexpropertymanagement.util.onlyNew
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.exhaustive
import com.example.tmcommonkotlin.logz
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.frag_login.*
import kotlinx.coroutines.Job


class LoginFrag : OXFragment(isToolbarEnabled = false) {

    lateinit var mBinding: FragLoginBinding
    val navController by lazy { this.findNavController() }
    val loginVM: LoginVM by viewModels()
    val compositeDisposable by lazy { CompositeDisposable() }
    val args by lazy { arguments?.let { LoginFragArgs.fromBundle(it) } }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_login, container, false)
        mBinding.lifecycleOwner = this
        mBinding.loginVM = loginVM
        setupClickListeners()
        setupObserver()
        return mBinding.root
    }

    private fun setupClickListeners() {
        mBinding.textviewNewUserClickHere.setOnClickListener {
            navController.navigate(R.id.fragRegister)
        }
        mBinding.btnLoginSend.setOnClickListener {
            loginVM.tryLogin()
            logz("loginSend clicked")
        }
    }

    private fun setupObserver() {
        loginVM.globalRepo.streamLoginAttemptResult.onlyNew(viewLifecycleOwner)
            .observe(viewLifecycleOwner, Observer {
                if (it is TryLoginResult.Success) {
                    when (args?.ReasonForLoginInt) {
                        ReasonForLogin.TriedToAddProperty.ordinal -> navController.navigateUp()
                        else -> navController.navigate(R.id.fragProperties)
                    }
                } else if (it is TryLoginResult.Failure) {
                    val msg = when (it) {
                        is TryLoginResult.Failure.IncorrectEmail -> "Email is not registered"
                        is TryLoginResult.Failure.TryIn5Mins -> "Too many login attempts. Try again later."
                        is TryLoginResult.Failure.UnknownMsg -> it.msg
                        is TryLoginResult.Failure.Unknown -> "Login Failed"
                        is TryLoginResult.Failure.InvalidInput -> "Invalid input"
                    }.exhaustive
                    logz("Login Failed:${it}")
                    easyToast(msg, Toast.LENGTH_LONG)
                }
            })
        loginVM.isLockedOut.observe(viewLifecycleOwner, Observer {
            mBinding.btnLoginSend.isEnabled = it != true
        })
    }


    override fun onStart() {
        super.onStart()
        setupViews()
    }

    private fun setupViews() {
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