package com.example.opulexpropertymanagement.di

import com.example.opulexpropertymanagement.layers.repo.RegisterRepo
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules= arrayOf(ApplicationModuleZ::class))
interface ApplicationComponentZ {
    fun injectRegisterRepo(repo: RegisterRepo)
}