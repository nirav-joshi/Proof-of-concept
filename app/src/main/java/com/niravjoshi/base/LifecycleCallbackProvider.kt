package com.niravjoshi.proof_of_concept.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * [LifecycleCallbackProvider] :
 *
 * Class used to provide callbacks from `Activity/Fragment` with specified logic using Method expressions.
 * Mainly used to provide hacky way to overcome issue of method calls between **binding create method & view ready**.
 *
 * Previously without this class, call was made like `onViewReady() -> onCreated() of binding class`.
 * Issue was resolved by providing proper callback to overcome this reverse callback issue due to
 * binding class receives callback of `onCreated()` with delay.
 *
 * Lifecycle doc suggests it *~700ms delay* is necessary for their internal architecture.
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 10/15/2019
 */
class LifecycleCallbackProvider(
    val lifecycle: Lifecycle,
    val onViewReadyCallback: (() -> Unit)? = null
) : LifecycleObserver {
    private var callShouldHappen = true

    /**
     * Method to provide callback when [Lifecycle.Event.ON_START] happens,
     * It must be in [Lifecycle.State.CREATED] state to provide this callback.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun afterCreate() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED) && callShouldHappen) {
            onViewReadyCallback?.invoke()
            callShouldHappen = false
        }
    }
}