package com.example.color_app_remake.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.color_app_remake.data.remote.Endpoints.BASE_URL
import com.example.color_app_remake.ui.networkConnectivity.ConnectivityObserver
import com.example.color_app_remake.ui.networkConnectivity.NetworkConnectivity
import com.example.color_app_remake.ui.networkConnectivity.NetworkConnectivityImpl
import com.example.color_app_remake.ui.networkConnectivity.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(
        connectivityManager: ConnectivityManager
    ): NetworkConnectivity {
        return NetworkConnectivityImpl(connectivityManager)
    }
}