package com.example.testapplication.ui.main.injection

import androidx.lifecycle.ViewModel
import com.example.testapplication.ui.main.injection.ViewModelKey
import com.example.testapplication.ui.main.viewmodel.MainViewModel
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@InstallIn(SingletonComponent::class)
@Module
object ViewModuleInjects {

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun rocketListViewModel(
        objectMapper: ObjectMapper
    ): ViewModel {
        return MainViewModel(objectMapper)
    }
}