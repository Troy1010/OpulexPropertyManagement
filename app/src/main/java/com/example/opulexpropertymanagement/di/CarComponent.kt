package com.example.opulexpropertymanagement.di

import com.example.opulexpropertymanagement.aa.repo.network.INetworkClient
import dagger.Component

@Component
interface CarComponent {
    fun provideCar(): Car
}