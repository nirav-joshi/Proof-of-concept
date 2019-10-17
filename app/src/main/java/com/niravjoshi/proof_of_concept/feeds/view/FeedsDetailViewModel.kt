package com.niravjoshi.proof_of_concept.feeds.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.niravjoshi.proof_of_concept.concepts.data.FeedsDetailRepository
import com.niravjoshi.proof_of_concept.concepts.model.FeedDetailDTO
import com.niravjoshi.proof_of_concept.concepts.model.FeedsDTO


/**
 * [FeedsDetailViewModel] :
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 7/11/2019
 */
class FeedsDetailViewModel : ViewModel() {
    val feedsLiveData: LiveData<List<FeedDetailDTO?>> = MutableLiveData()

    fun getFeeds() {
        feedsDetailRepository.getFeeds {
            (feedsLiveData as? MutableLiveData<*>)?.value=it
        }
    }

    fun refreshFeeds(){
        feedsDetailRepository.clearAllLocalDataBase()
        getFeeds()
    }
    private val feedsDetailRepository by lazy {
        return@lazy FeedsDetailRepository.getInstance()
    }


}