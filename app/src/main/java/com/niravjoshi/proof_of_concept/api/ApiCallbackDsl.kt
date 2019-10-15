@file:JvmName("ApiCallbackDsl")

package com.niravjoshi.proof_of_concept.api

/* @author : Nirav Joshi
* @version 1.0.0
* @since 10/15/2019
*/
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T?>?.enqueueOn(): CallbackImpl<T?>? {
    val callback = CallbackImpl<T?>()
    this?.enqueue(callback)
    return callback
}

infix fun <T> CallbackImpl<T?>?.success(success: (Call<T?>, Response<T?>) -> Unit): CallbackImpl<T?>? {
    this?.onSuccess = success
    return this
}

infix fun <T> CallbackImpl<T?>?.failure(failure: (Call<T?>, t: Throwable) -> Unit): CallbackImpl<T?>? {
    this?.onFailure = failure
    return this
}

class CallbackImpl<T> : Callback<T?> {
    var onFailure: ((Call<T?>, t: Throwable) -> Unit)? = null
    var onSuccess: ((call: Call<T?>, response: Response<T?>) -> Unit)? = null

    override fun onFailure(call: Call<T?>, t: Throwable) {
        this.onFailure?.invoke(call, t)
    }

    override fun onResponse(call: Call<T?>, response: Response<T?>) {
        this.onSuccess?.invoke(call, response)
    }
}