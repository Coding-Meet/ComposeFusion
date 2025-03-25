package com.example.composefusion.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast


fun Context.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val cap = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        cap.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}
