package co.com.test.extention

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast

/**
 * @desc this function use for gone(Hide) view
 *
 **/
fun View.beGone() {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

/**
 * @desc this function use for visible view
 *
 **/
fun View.beVisible() {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

/**
 * @desc this function use for invisible view
 *
 **/
fun View.beInVisible() {
    this.visibility = View.INVISIBLE
}

/**
 * @desc this function use show toast message
 * @param context - pass context of activity or fragment
 **/
fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

/**
 * @desc this function use for make view click
 **/
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }

/**
 * @desc this function use redirect one activity to another activity with bundle data
 * @param bundle - pass data in bundle
 **/
inline fun <reified T : Activity> Activity.startActivityInline(bundle: Bundle? = null) {
    if (bundle != null) {
        startActivity(
            Intent(this.applicationContext, T::class.java)
                .putExtras(bundle).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    } else {
        startActivity(
            Intent(this.applicationContext, T::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }
}

/**
 * @desc this function use for check internet available or not
 **/
fun Context.isOnline(): Boolean {
    val connMgr = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connMgr.getNetworkCapabilities(connMgr.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }

    } else {
        val activeNetworkInfo = connMgr.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
    return false
}
