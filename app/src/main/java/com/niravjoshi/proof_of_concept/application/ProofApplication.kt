package com.niravjoshi.proof_of_concept.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate


/**
 * [ProofApplication] :
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 10/15/2019
 */
class ProofApplication : Application() {
    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }

        @Volatile
        @JvmStatic
        lateinit var context: ProofApplication

        @JvmStatic
        fun isNetworkConnected(): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val activeNetwork = connectivityManager?.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                // Left out because "Unused"
            }

            override fun onActivityStarted(activity: Activity?) {
                // Left out because "Unused"
            }

            override fun onActivityResumed(activity: Activity?) {
                // Left out because "Unused"
            }

            override fun onActivityPaused(activity: Activity?) {
                // Left out because "Unused"
            }

            override fun onActivityStopped(activity: Activity?) {
                // Left out because "Unused"
            }

            override fun onActivityDestroyed(activity: Activity?) {
                // Left out because "Unused"
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                // Left out because "Unused"
            }
        })
    }


}