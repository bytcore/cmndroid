package com.bytcore.cmndroid.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.bytcore.cmndroid.orFalse

object NetworkUtils {
    fun checkConnection(context: Context?, block: (connected: Boolean) -> Unit) {
        block.invoke(isOnline(context))
    }

    private fun isOnline(context: Context?): Boolean {
        context ?: return false

        val connectivityManager =
                context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            val capabilities = connectivityManager
                    .getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false

            return capabilities.hasTransport(TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(TRANSPORT_WIFI)
        }

        return connectivityManager.activeNetworkInfo?.isConnected.orFalse()
    }
}
