package com.example.opulexpropertymanagement.test_di

import com.example.opulexpropertymanagement.di.AppComponent
import com.example.opulexpropertymanagement.layers.data_layer.Repo

interface TestAppComponent: AppComponent {
    override fun getRepo(): Repo
}