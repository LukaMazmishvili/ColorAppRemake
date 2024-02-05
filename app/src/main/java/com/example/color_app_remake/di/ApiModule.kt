package com.example.color_app_remake.di

import com.example.color_app_remake.data.remote.service.ColorService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideColorService(retrofit: Retrofit): ColorService {
        return retrofit.create(ColorService::class.java)
    }
}