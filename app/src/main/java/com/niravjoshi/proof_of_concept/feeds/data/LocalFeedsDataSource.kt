package com.niravjoshi.proof_of_concept.concepts.data

import com.niravjoshi.proof_of_concept.application.ProofApplication
import com.niravjoshi.proof_of_concept.concepts.model.FeedDetailDTO
import com.niravjoshi.proof_of_concept.concepts.model.FeedsDTO
import com.niravjoshi.proof_of_concept.database.ConceptDatabase
import com.niravjoshi.proof_of_concept.util.getDefaultPreference
import com.niravjoshi.proof_of_concept.util.putValue
import com.niravjoshi.proof_of_concept.util.runInBackground

/**
 * LocalFeedsDataSource :
 *
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 10/14/2019
 */
class LocalFeedsDataSource {
    object HOLDER {
        val instance = LocalFeedsDataSource()
    }

    private val database by lazy {
        return@lazy ConceptDatabase.instance
    }

    companion object {
        @JvmStatic
        fun getInstance() = HOLDER.instance
    }

    fun getFeeds(): List<FeedDetailDTO>? {
        return database?.feedDao?.runInBackground { getAllFeeds() }
    }

    fun addFeeds(feedsDTO: FeedsDTO?) {
        ProofApplication.context.getDefaultPreference()
            .putValue("title", feedsDTO?.mFeedTitle.toString())
        feedsDTO?.mFeedsList?.let {
            if (!it.isNullOrEmpty()) {
                clearFeeds()
                database?.feedDao?.runInBackground { addAllFeeds(it) }
            }
        }

    }

    fun getFeedCounts(): Long {
        return database?.feedDao?.runInBackground { getFeedsCount() } ?: 0
    }

    fun clearFeeds() {
        database?.feedDao?.runInBackground { this.clearFeedsTable() }
    }
}

