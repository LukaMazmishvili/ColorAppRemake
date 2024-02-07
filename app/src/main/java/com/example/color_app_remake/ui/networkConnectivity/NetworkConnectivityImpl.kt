package com.example.color_app_remake.ui.networkConnectivity

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkConnectivityImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : NetworkConnectivity {

    override fun isConnectedToWifi(): Flow<Boolean> = flow {
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        if (networkCapabilities != null) {
            emit(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
        }
    }

}