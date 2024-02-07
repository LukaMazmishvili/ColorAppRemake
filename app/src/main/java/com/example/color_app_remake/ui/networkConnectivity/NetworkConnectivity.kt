package com.example.color_app_remake.ui.networkConnectivity

import kotlinx.coroutines.flow.Flow

interface NetworkConnectivity {
    fun isConnectedToWifi(): Flow<Boolean>
}