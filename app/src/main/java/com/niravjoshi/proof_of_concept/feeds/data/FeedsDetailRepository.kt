package com.niravjoshi.proof_of_concept.concepts.data

import com.niravjoshi.proof_of_concept.application.ProofApplication
import com.niravjoshi.proof_of_concept.concepts.model.FeedDetailDTO
import com.niravjoshi.proof_of_concept.concepts.model.FeedsDTO

/**
 * [FeedsDetailRepository] :
 *
 * Repository class used to provide connection to API end-points or Database for login functionality
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 9/25/2019
 */
class FeedsDetailRepository private constructor() {
    object HOLDER {
        val instance = FeedsDetailRepository()
    }

    companion object {
        @JvmStatic
        fun getInstance() = HOLDER.instance
    }

    private val localDataSource by lazy {
        return@lazy LocalFeedsDataSource.getInstance()
    }

    private val remoteDataSource by lazy {
        return@lazy RemoteFeedsDataSource.getInstance()
    }



    fun addFeedsToLocal(feedsDTO: FeedsDTO) {
        localDataSource.addFeeds(feedsDTO)
    }

    fun clearAllLocalDataBase() {
        localDataSource.clearFeeds()
    }


    fun getFeeds(onFeedsCallback: (List<FeedDetailDTO>?) -> Unit) {
            if(localDataSource.getFeedCounts()==0L){
                remoteDataSource.getFeeds{
                    localDataSource.addFeeds(it)
                    onFeedsCallback(localDataSource.getFeeds())
                }
            }else {
                onFeedsCallback(localDataSource.getFeeds())
            }

    }


}