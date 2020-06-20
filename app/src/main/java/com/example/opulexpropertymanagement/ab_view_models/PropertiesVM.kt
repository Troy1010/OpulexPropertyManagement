package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.PropertiesRepo

class PropertiesVM: ViewModel() {
    val repo = PropertiesRepo()
}