package com.niravjoshi.proof_of_concept.feeds

import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.niravjoshi.proof_of_concept.R
import com.niravjoshi.proof_of_concept.adapters.BindingRecyclerAdapter
import com.niravjoshi.proof_of_concept.feeds.view.FeedsActivity
import com.niravjoshi.proof_of_concept.feeds.view.FeedsDetailViewModel
import com.niravjoshi.proof_of_concept.utils.ToolbarMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random


/**
 * [FeedsDataAssertionTest] :
 *
 *
 * @author Nirav Joshi
 * @version 1.0.0
 * @since 21-10-2019
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class FeedsDataAssertionTest {
    companion object {
        //We assert initial title to verify integrity
        const val INITIAL_TITLE = "Loading data"
        //Key for title to be load from SharedPreference
        const val PREF_KEY_TITLE = "title"
    }

    @get:Rule
    val feedsRule = ActivityTestRule(FeedsActivity::class.java)
    var feedsActivity: FeedsActivity? = null
    lateinit var viewModel: FeedsDetailViewModel

    @Before
    fun setUp() {
        feedsActivity = feedsRule.activity
        feedsActivity?.let {
            viewModel = ViewModelProviders.of(it)[FeedsDetailViewModel::class.java]
        }
        if (!this::viewModel.isInitialized) {
            throw UninitializedPropertyAccessException("ViewModel is not initialized yet!")
        }
        //(viewModel.feedsLiveData as? MutableLiveData)?.postValue(emptyList())
    }

    @Test
    fun assertTitleTest() {
        val preference = feedsActivity?.sharedPreference
        val actionBar = onView(isAssignableFrom(Toolbar::class.java))
        actionBar?.check(matches(isDisplayed()))
        haltThread()
        actionBar?.check(
            matches(
                ToolbarMatchers.withToolbarTitle(
                    preference?.getString(
                        PREF_KEY_TITLE,
                        ""
                    ) ?: ""
                )
            )
        )
    }

    @Test
    fun assertRecyclerViewData() {
        val recyclerView = onView(isAssignableFrom(RecyclerView::class.java))
        doesMatchingRecyclerViewFound(recyclerView)
        val position = getRandomIndex()
        val data = viewModel.feedsLiveData.value?.get(position)
        recyclerView?.perform(
            RecyclerViewActions.scrollToPosition<BindingRecyclerAdapter.BindingViewHolder>(
                position
            )
        )
        haltThread(500)
        recyclerView?.check(
            matches(
                atPositionOnView(
                    position,
                    withText(data?.mTitle),
                    R.id.tv_title
                )
            )
        )
        recyclerView?.check(
            matches(
                atPositionOnView(
                    position,
                    withText(data?.mDescription),
                    R.id.tv_desc
                )
            )
        )
    }

    private fun doesMatchingRecyclerViewFound(recyclerView: ViewInteraction?) {
        recyclerView?.check(matches(withId(R.id.rv_feeds)))
    }

    private fun getRandomIndex(): Int {
        return Random.nextInt(0, viewModel.feedsLiveData.value?.size ?: 0)
    }

    private fun atPositionOnView(
        position: Int,
        itemMatcher: Matcher<View>,
        @NonNull targetViewId: Int
    ): Matcher<View> {

        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has view id $itemMatcher at position $position")
            }

            public override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                val targetView = viewHolder?.itemView?.findViewById<View>(targetViewId)
                return itemMatcher.matches(targetView)
            }
        }
    }

    fun haltThread(millis: Long = 1000L) {
        Thread.sleep(millis)
    }

    @After
    fun tearDown() {
        feedsActivity = null
    }
}