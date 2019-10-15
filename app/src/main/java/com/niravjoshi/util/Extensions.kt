@file:JvmName("ExtensionsKt")

package com.niravjoshi.proof_of_concept.util

import android.view.View


private const val TAG = "Extensions"

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun <T, OBJ> OBJ.runInBackground(code: OBJ.() -> T?): T? {
    var t: T? = null
    val runnable = object : Runnable {
        override fun run() {
            t = code()
        }

        fun getValue() = t
    }
    val thread = Thread(runnable)
    thread.start()
    thread.join()
    return runnable.getValue()
}

