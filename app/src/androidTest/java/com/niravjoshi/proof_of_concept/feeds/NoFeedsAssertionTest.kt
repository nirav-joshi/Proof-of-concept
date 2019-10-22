package com.niravjoshi.proof_of_concept.feeds

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.niravjoshi.proof_of_concept.R
import com.niravjoshi.proof_of_concept.feeds.view.FeedsActivity
import com.niravjoshi.proof_of_concept.feeds.view.FeedsDetailViewModel
import com.niravjoshi.proof_of_concept.haltThread
import com.niravjoshi.proof_of_concept.utils.ToolbarMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * [NoFeedsAssertionTest] :
 *
 *
 * @author Nirav Joshi
 * @version 1.0.0
 * @since 21-10-2019
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class NoFeedsAssertionTest {
    @get:Rule
    val feedsRule = ActivityTestRule(FeedsActivity::class.java)
    var feedsActivity: FeedsActivity? = null
    private lateinit var viewModel: FeedsDetailViewModel

    @Before
    fun setUp() {
        feedsActivity = feedsRule.activity
        feedsActivity?.let {
            viewModel = ViewModelProviders.of(it)[FeedsDetailViewModel::class.java]
        }
        if (!this::viewModel.isInitialized) {
            throw UninitializedPropertyAccessException("ViewModel is not initialized yet!")
        }
        (viewModel.feedsLiveData as? MutableLiveData)?.postValue(emptyList())
        feedsActivity?.runOnUiThread {
            feedsActivity?.supportActionBar?.title = feedsActivity?.getString(R.string.app_name)
        }
    }

    @Test
    fun assertTitleTest() {
        val actionBar = onView(isAssignableFrom(Toolbar::class.java))
        actionBar?.check(matches(isDisplayed()))
        haltThread()
        actionBar?.check(matches(ToolbarMatchers.withToolbarTitle(R.string.app_name)))
    }

    @Test
    fun assertNoDataText() {
        val recyclerView = onView(isAssignableFrom(RecyclerView::class.java))
        doesMatchingRecyclerViewFound(recyclerView)
        val noDataTv = onView(withId(R.id.tv_nodata))
        noDataTv?.check(matches(isDisplayed()))
        noDataTv?.check(matches(withText(R.string.no_data_found)))
    }

    private fun doesMatchingRecyclerViewFound(recyclerView: ViewInteraction?) {
        recyclerView?.check(matches(withId(R.id.rv_feeds)))
    }

    @After
    fun tearDown() {
        feedsActivity = null
    }
}