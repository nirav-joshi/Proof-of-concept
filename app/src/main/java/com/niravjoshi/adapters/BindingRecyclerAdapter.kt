package com.niravjoshi.proof_of_concept.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.niravjoshi.proof_of_concept.adapters.BindingRecyclerAdapter.BindingViewHolder

/**
 * [BindingRecyclerAdapter] :
 *
 * Base [RecyclerView] adapter that implements basic functionality to be used as [RecyclerView.Adapter].
 * It has basic implementation of [BindingRecyclerAdapter.Builder] class used as builder pattern.
 *
 * Class uses Data-binding, so implementation requires no `ViewHolder` because, it uses [BindingViewHolder].
 *
 * ### Example: ###
 *
 *     val adapter = BindingRecyclerAdapter.Builder()
 *             .setLayoutResId(R.layout.item_example)
 *             .onCreateHolderCallback { holder, adapter -> }
 *             .onBindViewHolderCallback { holder, pos, adapter -> }
 *             .build()
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 10/15/2019
 */
class BindingRecyclerAdapter private constructor(builder: Builder) :
    RecyclerView.Adapter<BindingViewHolder>() {
    companion object {
        // Tag for logcat.
        const val TAG = "BindingRecyclerAdapter"
    }

    /**
     * [MutableList] object containing [Any] nullable type
     */
    var list: MutableList<Any?> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    @LayoutRes
    private var itemResId: Int = -1
    private var callback: ((BindingViewHolder, Int, BindingRecyclerAdapter) -> Unit)?
    private var onCreateCallback: ((BindingViewHolder, BindingRecyclerAdapter) -> Unit)?
    private var hasStable: Boolean = false

    init {
        this.list = builder.list ?: ArrayList()
        this.itemResId = builder.itemResId
        this.callback = builder.callback
        this.onCreateCallback = builder.onCreateCallback
        this.hasStable = builder.hasStable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val holder = BindingViewHolder(
            DataBindingUtil.inflate<ViewDataBinding?>(
                LayoutInflater.from(parent.context),
                itemResId,
                parent,
                false
            ) as ViewDataBinding
        )
        onCreateCallback?.let { it(holder, this) }
        return holder
    }

    override fun getItemCount(): Int = if (list.isNotEmpty()) list.size else 0

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        callback?.let { it(holder, holder.adapterPosition, this) }
        holder.binding.executePendingBindings()
    }

    override fun getItemId(position: Int) =
        if (hasStable) position.toLong() else super.getItemId(position)

    /**
     * Clear all data from adapter
     */
    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    /**
     * Method to add all data to list which removes all previously added data
     *
     * @param data as [MutableList] of type [T] to be added
     */
    fun <T> addAllItems(data: MutableList<T>?) where T : Any? {
        data?.let { items ->
            list.clear()
            list.addAll(items)
            notifyDataSetChanged()
        }
    }

    /**
     * Method to add all data to list which append data at the end
     *
     * @param data as [MutableList] of type [T] to be added
     */
    fun <T> appendAll(data: MutableList<T>?) where T : Any? {
        data?.let { items ->
            val startIndex = list.size
            list.addAll(items)
            notifyItemRangeChanged(startIndex, items.size)
        }
    }

    /**
     * Method to add [data] to list at the end
     *
     * @param data as [T] to be added at end
     */
    fun <T> addItem(data: T) where T : Any? {
        list.add(data)
        notifyItemInserted(list.size - 1)
    }

    /**
     * Method to indicate that particular item at [position] as [data] item is updated
     *
     * @param position as [Int] indicating position of item
     * @param data as [T] to be update
     */
    fun <T> updateItem(position: Int, data: T) where T : Any? {
        list[position] = data
        notifyItemChanged(position, data)
    }

    /**
     * Method to remove item at [position]
     *
     * @param position as [Int] indicating position of item
     */
    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Method to notify change in item passed as [data]
     *
     * @param data as [T] indicating type of item
     */
    fun <T> notifyItemChanged(data: T) where T : Any? {
        notifyItemChanged(list.indexOf(data))
    }

    /**
     * [Builder] :
     *
     * Builder class for [BindingRecyclerAdapter] to provide builder pattern so that api changes doesn't cause refactor problem.
     *
     * Use this class in congestion with [BindingRecyclerAdapter] to create new object of [RecyclerView.Adapter]
     * and set it to your `RecyclerView` like usual.
     *
     * Check [BindingRecyclerAdapter] for more details.
     *
     * @author : Nirav Joshi
     * @version 1.0.0
     * @since 10/15/2019
     * @see [BindingRecyclerAdapter]
     */
    open class Builder(list: MutableList<Any?>? = ArrayList()) {
        /**
         * [MutableList] variable of [Any] type to hold list of data for this adapter
         */
        var list: MutableList<Any?>? = list
            private set
        /**
         * [Int] variable of [LayoutRes] type to hold item layout for this adapter
         */
        @LayoutRes
        var itemResId: Int = -1
            private set
        /**
         * Callback variable of Method expression type to provide referencing callback for [RecyclerView.Adapter.onBindViewHolder]
         */
        lateinit var callback: (BindingViewHolder, Int, BindingRecyclerAdapter) -> Unit
            private set
        /**
         * Callback variable of Method expression type to provide referencing callback for [RecyclerView.Adapter.onCreateViewHolder]
         */
        var onCreateCallback: ((BindingViewHolder, BindingRecyclerAdapter) -> Unit)? = null
            private set
        var hasStable: Boolean = false
            private set

        init {
            this.list = list
        }

        /**
         * Method to set layout resource id for this adapter,
         * holds [LayoutRes] id to be used inside [RecyclerView.Adapter.onCreateViewHolder] when inflating item layout
         *
         * @param itemResId as [Int] id of type [LayoutRes] to be inflated
         *
         * @return [Builder] to be used further for another setup method
         */
        fun setLayoutResId(@LayoutRes itemResId: Int): Builder {
            this.itemResId = itemResId
            return this
        }

        /**
         * Method to provide callback upon [RecyclerView.Adapter]'s [RecyclerView.Adapter.onCreateViewHolder] method,
         * indicates that [RecyclerView.Adapter.onCreateViewHolder] happened so that any implementation
         * should be resolved like setting up some click listeners.
         *
         * @param onCreateCallback as Method expression parameter for providing `onCreateViewHolder` callback.
         *
         * @return [Builder] to be used further for another setup method
         */
        fun onCreateHolderCallback(onCreateCallback: (BindingViewHolder, BindingRecyclerAdapter) -> Unit): Builder {
            this.onCreateCallback = onCreateCallback
            return this
        }

        /**
         * Method to provide callback upon [RecyclerView.Adapter]'s [RecyclerView.Adapter.onBindViewHolder] method,
         * indicates that [RecyclerView.Adapter.onBindViewHolder] happened so that any implementation
         * should be resolved like setting up some views and binding data to it.
         *
         * @param callback as Method expression parameter for providing `onBindViewHolder` callback.
         *
         * @return [Builder] to be used further for another setup method
         */
        fun onBindViewHolderCallback(callback: (BindingViewHolder, Int, BindingRecyclerAdapter) -> Unit): Builder {
            this.callback = callback
            return this
        }

        /**
         * Method to set boolean flag about setting stable ids of ViewHolder in from this adapter
         *
         * @param hasStable as [Boolean] to set if ViewHolder should have stable ids or not
         *
         * @return [Builder] to be used further for another setup method
         */
        fun hasStableIds(hasStable: Boolean): Builder {
            this.hasStable = hasStable
            return this
        }

        /**
         * Method finally builds new [BindingRecyclerAdapter] object or throw exception if setup is improper.
         *
         * @return [BindingRecyclerAdapter] newly created object when build is successful
         */
        fun build(): BindingRecyclerAdapter {
            return BindingRecyclerAdapter(this)
        }
    }

    /**
     * [BindingViewHolder] :
     *
     * ViewHolder class that holds [binding] object for data-binding values from list to item view.
     *
     * @author : Nirav Joshi
     * @version 1.0.0
     * @since 10/15/2019
     * @see [RecyclerView.ViewHolder]
     * @see [BindingRecyclerAdapter]
     */
    open class BindingViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)
}