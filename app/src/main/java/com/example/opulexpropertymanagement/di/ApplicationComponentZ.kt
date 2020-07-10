package com.example.opulexpropertymanagement.di

import com.example.opulexpropertymanagement.layers.data_layer.RegisterRepo
import com.example.opulexpropertymanagement.layers.data_layer.Repo
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules= arrayOf(ApplicationModuleZ::class))
interface ApplicationComponentZ {
    fun injectRegisterRepo(repo: RegisterRepo)

    fun getRepo(): Repo
}