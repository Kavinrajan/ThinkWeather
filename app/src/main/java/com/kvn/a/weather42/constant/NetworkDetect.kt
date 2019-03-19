package com.kvn.a.weather42.constant

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created to Detect Network Connectivity
 */

class NetworkDetect(private val _context: Context) {

    val isConnectingToInternet: Boolean
        get() {

            val cm = _context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val netInfo = cm.activeNetworkInfo
            if (netInfo != null) {
                if (netInfo.typeName.equals("MOBILE", ignoreCase = true)) {

                    if (netInfo.isConnected && netInfo.isAvailable) {

                        return true
                    }
                }

                if (netInfo.typeName.equals("WIFI", ignoreCase = true)) {

                    if (netInfo.isConnected && netInfo.isAvailable) {

                        return true
                    }
                }
            }

            return false
        }
}
