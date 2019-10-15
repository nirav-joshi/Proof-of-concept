package com.niravjoshi.proof_of_concept.base

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * [AbstractBinding] :
 *
 * ## Abstract class that represent binding of particular layout file provided by [AbstractBinding.binding]. ##
 *
 * Class provides [ViewDataBinding] object created on inflation to further use from base setup and hold UI logic separate from `Activity/Fragment`
 * to help to maintain code in such a way that `Activity/Fragment` doesn't become too much messy and can be maintained easily.
 *
 * Class also provides callback of [Lifecycle.Event.ON_CREATE] & [Lifecycle.Event.ON_DESTROY] attached to particular ViewLifeCycleObserver of `Activity/Fragment`
 * to do some initialization/destruction about it,
 * provides [AbstractBinding.lifecycle] object about it.
 *
 * Check out some methods & variables:
 *
 *   1. #[AbstractBinding.onCreated]
 *   2. #[AbstractBinding.onDestroy]
 *   3. [AbstractBinding.binding]
 *   4. [AbstractBinding.lifecycle]
 *   5. [AbstractBinding.context]
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 10/15/2019
 * @see [LifecycleObserver]
 * @see [ViewDataBinding]
 */
abstract class AbstractBinding<VB : ViewDataBinding> : LifecycleObserver {
    /**
     * Nullable [ViewDataBinding] object casted to layout binding provided by [VB]
     */
    var binding: VB? = null
    /**
     * Nullable [Lifecycle] object if any child need some lifecycle related method
     */
    var lifecycle: Lifecycle? = null
    /**
     * Nullable [Context] needed for any relational operations
     */
    val context: Context?
        get() {
            return binding?.root?.context
        }

    /**
     * Method that provides execution up on create of ViewLifeCycle usually mapped with [Lifecycle.Event.ON_CREATE]
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    abstract fun onCreated()

    /**
     * Method that provides execution up on destroy of ViewLifeCycle usually mapped with [Lifecycle.Event.ON_DESTROY]
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    abstract fun onDestroy()
}