package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.opulexpropertymanagement.models.streamable.GetPropertiesResult
import com.example.tmcommonkotlin.logz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertiesVM: ViewModel() {
    val properties by lazy { MediatorLiveData<List<Property>>() }
    val repo = Repo
    init {
        val user = UserVM.user.value
        if (user!=null) {
            emitProperties(user)
        }
        properties.addSource(repo.liveDataAddProperty) {
            val user = UserVM.user.value ?: return@addSource
            if (it is AddPropertyResult.Success) {
                emitProperties(user)
            }
        }
    }
    fun emitProperties(user: User) {
        viewModelScope.launch {
            val result = repo.getPropertiesByUser(user)
            val x = when (result) {
                is GetPropertiesResult.Failure -> emptyList()
                is GetPropertiesResult.Success -> result.properties
            }
            CoroutineScope(Dispatchers.Main).launch {
                properties.value = x
            }
        }
    }
}