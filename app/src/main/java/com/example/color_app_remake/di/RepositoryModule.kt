package com.example.color_app_remake.di

import com.example.color_app_remake.data.remote.repository.ColorsRepositoryImpl
import com.example.color_app_remake.data.remote.service.ColorService
import com.example.color_app_remake.domain.repository.ColorsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideColorsRepository(service: ColorService): ColorsRepository {
        return ColorsRepositoryImpl(service)
    }
}