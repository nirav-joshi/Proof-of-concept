package com.niravjoshi.proof_of_concept.concepts.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.niravjoshi.proof_of_concept.base.AbstractBinding
import com.niravjoshi.proof_of_concept.databinding.ActivityMainBinding
import com.niravjoshi.proof_of_concept.util.gone
import com.niravjoshi.proof_of_concept.util.show

/**
 * [FeedActivityBinder] :
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 10/14/2019
 */
class FeedActivityBinder() : AbstractBinding<ActivityMainBinding>(),
    SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        onRefreshCallback?.invoke()
    }

    override fun onCreated() {
        binding?.data = this
    }

    fun setRecyclerLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding?.rvFeeds?.layoutManager = layoutManager
    }

    override fun onDestroy() {
        binding?.data = null
    }

    var onRefreshCallback: (() -> Unit)? = null
    var swipeProgress: ObservableField<Pair<Boolean?, Boolean?>> =
        ObservableField(Pair(first = true, second = false))

    companion object {
        @JvmStatic
        @BindingAdapter(
            "imageUrl",
            requireAll = false
        )
        fun loadServerImage(
            imageView: ImageView?,
            imageUrl: String?
        ) {
            if (!imageUrl.isNullOrEmpty()) {
                imageView?.let { iv ->
                    Glide.with(iv.context)
                        .load(imageUrl)
                        .apply(RequestOptions().override(iv.width, iv.height))
                        .into(iv)
                    iv.show()
                }
            } else {
                imageView?.gone()
            }

        }


        @JvmStatic
        @BindingAdapter("setvisibility", requireAll = false)
        fun setVisibility(view: View?, textString: String?) {
            view?.let {
                if (textString.isNullOrEmpty()) {
                    it.gone()
                }else{
                    it.show()
                }
            }


        }
    }
}