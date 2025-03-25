package com.example.composefusion.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.telephony.TelephonyManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn

class NetworkConnectivityObserver(context: Context) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager


    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        .build()

    private val telephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager


    val isConnected = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val isValidated =
                    connectivityManager.getNetworkCapabilities(network)?.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_VALIDATED
                    ) ?: false
                trySend(isValidated)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                trySend(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(false)
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }
        }
        val simAvailable = TelephonyManager.SIM_STATE_ABSENT != telephonyManager.simState
        if (!simAvailable) {
            trySend(false)  // Ensure we reflect no connectivity if no SIM card is present
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(callback)
        } else {
            connectivityManager.registerNetworkCallback(networkRequest, callback)
        }

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}

class ConnectivityViewModel(
    connectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    val isConnected = connectivityObserver
        .isConnected
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )
}