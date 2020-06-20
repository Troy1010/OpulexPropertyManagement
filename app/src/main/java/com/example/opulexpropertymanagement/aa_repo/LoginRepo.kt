package com.example.opulexpropertymanagement.aa_repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.opulexpropertymanagement.util.LiveEvent
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object LoginRepo {
    val liveDataTryLogin by lazy { MutableLiveData<TryLoginResult>() }

    fun tryLogin(email: String, password: String) {
        val d = NetworkClient.tryLogin(email, password)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map {
                val responseString = it.string()
                if ("success" in responseString) {
                    val user = Gson().fromJson(responseString, User::class.java)
                    TryLoginResult.Success(user)
                } else {
                    TryLoginResult.Failure("Unknown error")
                }
            }.subscribe({ liveDataTryLogin.value = it})
    }
}