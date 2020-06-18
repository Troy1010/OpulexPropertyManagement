package com.example.opulexpropertymanagement.ui

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
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttemptResponse
import com.example.opulexpropertymanagement.ui.inheritables.OXFragment
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.exhaustive
import com.example.tmcommonkotlin.logz
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.frag_login.*


class FragLogin : OXFragment(isToolbarEnabled = false) {

    lateinit var mBinding: FragLoginBinding
    val navController by lazy { this.findNavController() }
    val userVM: UserVM by activityViewModels()
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
//            val email = mBinding.edittextEmail.text.toString()
//            val password = mBinding.edittextPassword.text.toString()
//            userVM.tryLogin(User(email, password))
            val email = mBinding.textinputeditEmail.text.toString()
            val password = mBinding.textinputeditPassword.text.toString()
            userVM.tryLogin(email, password)
        }
//        userVM.user
//            .observe(viewLifecycleOwner, Observer {user ->
//                logz("FragLogin`new user:$user")
//                if (user!=null) {
//                    when (args?.ReasonForLoginInt) {
//                        ReasonForLogin.Properties.ordinal -> {
//                            logz("Navigate to fragProperties")
//                            val directions = FragLoginDirections.actionGlobalFragProperties(true)
//                            navController.navigate(directions)
//                        }
//                        else -> navController.navigate(R.id.fragHome)
//                    }
//                } else {
//                    easyToast(requireActivity(), "Login Failed")
//                }
//            })
        compositeDisposable.add(
            userVM.loginAttemptResponse
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when (it) {
                        is StreamableLoginAttemptResponse.Error -> {
                            easyToast(requireActivity(), "Login Failed")
                            logz(it.msg)
                        }
                        is StreamableLoginAttemptResponse.Success -> {
                            val directions = FragLoginDirections.actionGlobalFragProperties(false)
                            navController.navigate(directions)
                        }
                    }
                }
        )
//        compositeDisposable.add(
//            userVM.userStateStream
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .skip(1)
//                .subscribe({
//                    if (it.wantsLogin && !it.hasLogin) {
//                        activity?.easyToast("Login failed")
//                    } else if (it.wantsLogin && it.hasLogin) {
//                        logz("Navigate!")
//                        navController.navigate(R.id.action_fragLogin_to_fragHome)
//                    }
//                }, {
//                    logz(it.message?:"")
//                })
//        )
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

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}