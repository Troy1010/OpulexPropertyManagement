package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragLoginBinding
import com.example.opulexpropertymanagement.models.ReasonForLogin
import com.example.opulexpropertymanagement.ui.inheritables.OXFragment
import kotlinx.android.synthetic.main.frag_login.*


class FragLogin : OXFragment() {

    lateinit var mBinding: FragLoginBinding
    val navController by lazy { this.findNavController() }
    val userVM: UserVM by viewModels()

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
            val email = mBinding.edittextEmail.text.toString()
            val password = mBinding.edittextPassword.text.toString()
            userVM.tryLogin(User(email, password))
        }
        mBinding.btnGoBack.setOnClickListener {
            navController.navigateUp()
        }
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
        val args = arguments?.let { FragLoginArgs.fromBundle(it) }
        if (args?.ReasonForLoginInt == ReasonForLogin.Properties.ordinal) {
            textview_reason_for_login.text =
                "In order to see your properties, you must be logged in."
        }
    }
}