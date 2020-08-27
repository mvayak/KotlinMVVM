package co.com.test.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import co.com.test.extention.isOnline

/**
 * @desc this is BroadcastReceiver class use for check internet connectivity
 * @author : Mahesh Vayak
 * @required
 **/
class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        connectivityReceiverListener?.let {
            context?.apply {
                it.onNetworkConnectionChanged(isOnline())
            }
        }
    }


    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
}