package com.example.pg_mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.grocerygo.activities_and_frags.Inheritables.TMFragment
import com.example.pg_mvvm.databinding.FragLoginBinding
import com.example.pg_mvvm.models.User
import com.example.pg_mvvm.view_models.UserVM
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logz
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class FragLogin : TMFragment() {

    lateinit var mBinding: FragLoginBinding
    val navController by lazy { this.findNavController() }
    val userVM: UserVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.frag_login, container, false
        )
        mBinding.textviewNewUserClickHere.setOnClickListener {
            navController.navigate(R.id.fragRegister)
        }
        mBinding.btnLoginSend.setOnClickListener {
            val email = mBinding.edittextEmail.text.toString()
            val password = mBinding.edittextPassword.text.toString()
            userVM.tryLogin(User(email, password))
        }
        compositeDisposable.add(
            userVM.userStateStream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skip(1)
                .subscribe({
                    if (it.wantsLogin && !it.hasLogin) {
                        activity?.easyToast("Login failed")
                    } else if (it.wantsLogin && it.hasLogin) {
                        logz("Navigate!")
                        navController.navigate(R.id.action_fragLogin_to_fragHome)
                    }
                }, {
                    logz(it.message?:"")
                })
        )
        return mBinding.root
    }
}