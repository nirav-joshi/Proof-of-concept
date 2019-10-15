package com.niravjoshi.proof_of_concept.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.niravjoshi.proof_of_concept.util.getDefaultPreference

/**
 * [AbstractBaseActivity] :
 *
 * ## **Base** activity class provides setup methods and functionality useful across entire app. ##
 * This activity provides **abstract method** [AbstractBaseActivity.setUpBuilder]
 * that accepts configurations needed for activity to work properly like `contentView` from [AbstractBaseActivity.builder].
 *
 *
 * Calls [AbstractBaseActivity.onViewReady] when setup completes,
 * so that *child activity* receives callback regarding [AppCompatActivity.onCreate] happened.
 *
 * Check out some methods & variables:
 *
 *   1. #[AbstractBaseActivity.setUpBuilder]
 *   2. #[AbstractBaseActivity.onViewReady]
 *   3. #[AbstractBaseActivity.showBackArrow]
 *   4. [AbstractBaseActivity.builder]
 *   5. [AbstractBaseActivity.rootView]
 *   6. [AbstractBaseActivity.sharedPreference]
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 10/15/2019
 */
abstract class AbstractBaseActivity : AppCompatActivity() {
    /**
     * Variable acts as utility for this activity & provides necessary fields
     */
    var builder: AbstractActivityBuilder? = null
        private set
    /**
     * Root [ViewGroup] of this activity
     */
    var rootView: ViewGroup? = null
        private set
    /**
     * provides [SharedPreferences] object to handle local I/O
     */
    val sharedPreference: SharedPreferences? by lazy {
        return@lazy this.getDefaultPreference()
    }
    private var lifecycleCallbackProvider: LifecycleCallbackProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        builder = setUpBuilder()
        setUiContent()
        lifecycleCallbackProvider = LifecycleCallbackProvider(lifecycle) {
            onViewReady(savedInstanceState)
        }
        provideLifeCycle()
    }

    /**
     * Method helps you set back arrow on Action bar to navigate up/back from it.
     */
    fun showBackArrow() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setUiContent() {
        if (builder?.abstractBinding != null) {
            builder?.contentView?.let {
                builder?.abstractBinding?.binding = DataBindingUtil.setContentView(this, it)
            }
            rootView = builder?.abstractBinding?.binding?.root as? ViewGroup
        } else {
            builder?.contentView?.let {
                setContentView(it)
                rootView = window?.decorView?.findViewById<ViewGroup?>(android.R.id.content)
            }
        }
    }

    private fun provideLifeCycle() {
        builder?.abstractBinding?.let {
            this@AbstractBaseActivity.lifecycle.addObserver(it)
            it.lifecycle = this@AbstractBaseActivity.lifecycle
            it.binding?.lifecycleOwner = this@AbstractBaseActivity
        }
        lifecycleCallbackProvider?.let {
            this@AbstractBaseActivity.lifecycle.addObserver(it)
        }
    }

    private fun revokeLifeCycle() {
        builder?.abstractBinding?.let {
            this@AbstractBaseActivity.lifecycle.removeObserver(it)
            it.lifecycle = null
        }
        lifecycleCallbackProvider?.let {
            this@AbstractBaseActivity.lifecycle.removeObserver(it)
        }
        lifecycleCallbackProvider = null
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        revokeLifeCycle()
    }

    /**
     * Abstract method provides [AbstractActivityBuilder] needed to build this base activity.
     * Setup some base parameters from [AbstractActivityBuilder] that this base requires and rest of it would be handled successfully.
     *
     * @return [AbstractActivityBuilder] object required by this [AbstractBaseActivity] class.
     */
    abstract fun setUpBuilder(): AbstractActivityBuilder

    /**
     * Callback method provided by this [AbstractBaseActivity] when all setup of [onCreate] completes
     */
    abstract fun onViewReady(savedInstanceState: Bundle?)
}