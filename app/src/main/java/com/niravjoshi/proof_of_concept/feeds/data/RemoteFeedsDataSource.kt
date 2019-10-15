package com.niravjoshi.proof_of_concept.concepts.data

import com.niravjoshi.proof_of_concept.api.RetrofitSingleton
import com.niravjoshi.proof_of_concept.api.enqueueOn
import com.niravjoshi.proof_of_concept.api.failure
import com.niravjoshi.proof_of_concept.api.success
import com.niravjoshi.proof_of_concept.application.ProofApplication
import com.niravjoshi.proof_of_concept.concepts.model.FeedsDTO

/**
 * RemoteFeedsDataSource :
 *
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 10/14/2019
 */
class RemoteFeedsDataSource {

    object HOLDER {
        val instance = RemoteFeedsDataSource()
    }

    companion object {
        @JvmStatic
        fun getInstance() = HOLDER.instance
    }

    private val mApiService by lazy {
        return@lazy RetrofitSingleton.getInstance().provideApiService()
    }

    fun getFeeds(onFeedsCallback: (FeedsDTO?) -> Unit) {
        if (ProofApplication.isNetworkConnected()) {
            mApiService.getFeedsDetails().enqueueOn().success { _, response ->
                when {
                    response.isSuccessful && response.code() == 200 -> {
                        onFeedsCallback(response.body())
                    }
                    else -> {
                        onFeedsCallback(response.body())
                    }
                }
            } failure { _, t ->
                onFeedsCallback(null)
            }
        }
    }
}