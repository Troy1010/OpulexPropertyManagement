package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.LoginRepo
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.util.convertRXToLiveData
import com.example.opulexpropertymanagement.util.toLiveData

class LoginVM : ViewModel() {
    val repo = LoginRepo
}