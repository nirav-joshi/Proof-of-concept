package com.niravjoshi.proof_of_concept.concepts.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.niravjoshi.proof_of_concept.concepts.model.FeedDetailDTO
import com.niravjoshi.proof_of_concept.database.ConceptDataContract

/**
 * [FeedDao] : Interface provided to expose some method related to CRUD operations on database for [FeedDetailDTO]
 *
 * @author Nirav Joshi
 * @since 10/14/2019
 * @version 1.0.0
 */

@Dao
interface FeedDao {

    /**
     *  insert all feeds to the database
     *  @param detailList as [List] of [FeedDetailDTO]s object
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllFeeds(detailList: List<FeedDetailDTO>)

    /**
     *  returns list of feeds [FeedDetailDTO]
     */
    @Query("SELECT * FROM ${ConceptDataContract.FEED_DETAIL_TABLE_NAME}")
    fun getAllFeeds(): List<FeedDetailDTO>

    /**
     *  returns count (no of rows) from the table [ConceptDataContract.FEED_DETAIL_TABLE_NAME]
     */
    @Query("SELECT COUNT(${ConceptDataContract.FEED_ID}) FROM ${ConceptDataContract.FEED_DETAIL_TABLE_NAME}")
    fun getFeedsCount(): Long

    /**
     * Clear rows from the table [ConceptDataContract.FEED_DETAIL_TABLE_NAME]
     */
    @Query("DELETE FROM ${ConceptDataContract.FEED_DETAIL_TABLE_NAME}")
    fun clearFeedsTable()

}