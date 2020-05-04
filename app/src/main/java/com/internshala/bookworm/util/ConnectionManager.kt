package com.internshala.bookworm.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo

class ConnectionManager {
    fun checkConnectivity(context: Context):Boolean{
        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val checkConnection:NetworkInfo? = connectionManager.activeNetworkInfo

        return if (checkConnection?.isConnected != null) {
            checkConnection.isConnected
        } else {
            false
        }
    }
}