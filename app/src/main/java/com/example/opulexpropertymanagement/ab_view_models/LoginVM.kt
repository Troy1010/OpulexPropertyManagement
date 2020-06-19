package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.util.convertRXToLiveData

class LoginVM: ViewModel() {
    val liveDataTryLogin by lazy { convertRXToLiveData(Repo.streamTryLogin) }
    fun tryLogin(email: String, password: String) {
        Repo.tryLogin(email, password)
    }
}