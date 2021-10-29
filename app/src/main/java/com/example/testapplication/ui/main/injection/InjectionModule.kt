package com.example.testapplication.ui.main.injection

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object InjectionModule {

    @Provides
    @Singleton
    fun getObjectMapper(): ObjectMapper {
        return jacksonObjectMapper()
    }
}