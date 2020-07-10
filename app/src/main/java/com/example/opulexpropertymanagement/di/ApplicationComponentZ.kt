package com.example.opulexpropertymanagement.di

import com.example.opulexpropertymanagement.layers.data_layer.RegisterRepo
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules= arrayOf(ApplicationModuleZ::class))
interface ApplicationComponentZ {
    fun injectRegisterRepo(repo: RegisterRepo)
}