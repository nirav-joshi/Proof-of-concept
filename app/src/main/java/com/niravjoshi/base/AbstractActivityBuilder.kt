package com.niravjoshi.proof_of_concept.base

import android.app.Activity
import android.content.Intent
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

/**
 * [AbstractActivityBuilder] :
 *
 * Main **builder** class for Activity that is used as *Model* to hold data used in abstract activity class.
 *
 * Check out variables:
 *   1. [AbstractActivityBuilder.contentView]
 *   2. [AbstractActivityBuilder.abstractBinding]
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 10/15/2019
 *
 * @see [AbstractBaseActivity]
 */
class AbstractActivityBuilder {

    /**
     * Variable that hold layout `resource id` of Activity
     */
    @LayoutRes
    var contentView: Int? = null

    /**
     * Variable to be set if used **Data-Binding** to provide Generic logic for [AbstractBinding].
     */
    var abstractBinding: AbstractBinding<out ViewDataBinding>? = null
}

/**
 * DSL-Method to provide imperative logic for object creation of [AbstractActivityBuilder] with validations
 *
 * @param builder as lambda method parameter for setup as callback to apply
 *
 * @return [AbstractActivityBuilder]
 */
fun absActivityBuilder(builder: AbstractActivityBuilder.() -> Unit): AbstractActivityBuilder {
    val absActivityBuilder = AbstractActivityBuilder()
    absActivityBuilder.apply(builder)
    return when {
        (absActivityBuilder.contentView == null) or (absActivityBuilder.contentView == -1) -> {
            throw IllegalArgumentException("Content view must not be null or -1")
        }
        else -> {
            absActivityBuilder
        }
    }
}

/**
 * Extension method to launch activity class by simply passing [clazz] parameter,
 * provides additional callback [intent] if you want to pass some extras
 *
 * @param clazz of type [Class] as Activity to be launch
 * @param intent as callback parameter if you want to provide some intent extras of just simple leave as it is
 */
fun <T : Activity> Activity?.startNewActivity(
    clazz: Class<T>,
    intent: (Intent.() -> Unit)? = null
) {
    if (intent != null) {
        this?.startActivity(Intent(this, clazz).apply(intent))
    } else {
        this?.startActivity(Intent(this, clazz))
    }
}

/**
 * Extension method works same as [startNewActivity], only difference is passing request code [reqCode] to receive result on to.
 *
 * @param clazz of type [Class] as Activity to be launch
 * @param reqCode to track result of launched [Activity] in [Activity.onActivityResult]
 * @param intent as callback parameter if you want to provide some intent extras of just simple leave as it is
 */
fun <T : Activity> Activity?.startNewActivityForResult(
    clazz: Class<T>,
    reqCode: Int,
    intent: (Intent.() -> Unit)? = null
) {
    if (intent != null) {
        this?.startActivityForResult(Intent(this, clazz).apply(intent), reqCode)
    } else {
        this?.startActivityForResult(Intent(this, clazz), reqCode)
    }
}