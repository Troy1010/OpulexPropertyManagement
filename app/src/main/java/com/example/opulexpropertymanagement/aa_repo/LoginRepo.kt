package com.example.opulexpropertymanagement.aa_repo

import androidx.lifecycle.MutableLiveData

class LoginRepo {
    val streamLoginAttemptOverloadTimestamp = MutableLiveData<Int>()
    init {
        // load email:timestamp map from db
    }
}