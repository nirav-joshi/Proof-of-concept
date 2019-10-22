package com.niravjoshi.proof_of_concept.feeds.view

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.niravjoshi.proof_of_concept.BR
import com.niravjoshi.proof_of_concept.R
import com.niravjoshi.proof_of_concept.adapters.BindingRecyclerAdapter
import com.niravjoshi.proof_of_concept.application.ProofApplication.Companion.isNetworkConnected
import com.niravjoshi.proof_of_concept.base.AbstractBaseActivity
import com.niravjoshi.proof_of_concept.base.absActivityBuilder
import com.niravjoshi.proof_of_concept.concepts.model.FeedDetailDTO
import com.niravjoshi.proof_of_concept.util.gone
import com.niravjoshi.proof_of_concept.util.show

class FeedsActivity : AbstractBaseActivity() {

    companion object {
        val EXTRA_FLAG_CALL_API = "callShouldHappen"
    }

    private var feedsAdapter: BindingRecyclerAdapter? = null
    private val feedActivityBinder by lazy {
        return@lazy FeedActivityBinder()
    }

    private val feedsDetailViewModel by lazy {
        ViewModelProviders.of(this)[FeedsDetailViewModel::class.java]
    }

    override fun setUpBuilder() = absActivityBuilder {
        contentView = R.layout.activity_main
        abstractBinding = feedActivityBinder
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupRefreshCallbacks()
        setUpDataView()
        observeFeeds()
        if (savedInstanceState == null) {
            getFeeds()
        } else {
            if (savedInstanceState.keySet().contains(EXTRA_FLAG_CALL_API)
                && savedInstanceState.getBoolean(EXTRA_FLAG_CALL_API, false)
            )
                getFeeds()
        }
    }

    private fun setupRefreshCallbacks() {
        feedActivityBinder.onRefreshCallback = {
            if (isNetworkConnected()) {
                feedActivityBinder.swipeProgress.set(Pair(first = true, second = true))
                feedsDetailViewModel.refreshFeeds()
                Thread(Runnable {
                    Glide.get(this).clearDiskCache()
                })

            } else {
                feedActivityBinder.swipeProgress.set(Pair(first = true, second = false))
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    private fun setUpDataView() {
        feedsAdapter = BindingRecyclerAdapter.Builder(ArrayList())
            .setLayoutResId(R.layout.item_feed_layout)
            .onBindViewHolderCallback { holder, position, adapter ->
                val rowDto = adapter.list[holder.adapterPosition] as? FeedDetailDTO
                holder.binding.setVariable(BR.rowDTO, rowDto)
            }
            .build()
        feedActivityBinder.binding?.rvFeeds?.adapter = feedsAdapter
    }

    private fun getFeeds() {
        feedsDetailViewModel.getFeeds()
    }

    private fun observeFeeds() {
        feedsDetailViewModel.feedsLiveData.observe(this, Observer {
            when (resources?.configuration?.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    feedActivityBinder.setRecyclerLayoutManager(
                        StaggeredGridLayoutManager(
                            2,
                            StaggeredGridLayoutManager.VERTICAL
                        )
                    )
                }
                else -> {
                    feedActivityBinder.setRecyclerLayoutManager(
                        LinearLayoutManager(
                            this,
                            RecyclerView.VERTICAL, false
                        )
                    )
                }
            }
            feedActivityBinder.swipeProgress.set(Pair(first = true, second = false))
            title = this.sharedPreference?.getString("title", "")
            if (it?.isNullOrEmpty()==true) {
                feedActivityBinder.binding?.tvNodata?.show()
            }else{
                feedActivityBinder.binding?.tvNodata?.gone()
                feedsAdapter?.addAllItems(it.toMutableList())
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(EXTRA_FLAG_CALL_API, false)
        super.onSaveInstanceState(outState)
    }
}
