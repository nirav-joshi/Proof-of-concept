package com.niravjoshi.proof_of_concept.database

import com.niravjoshi.proof_of_concept.BuildConfig


/**
 * [ConceptDataContract] : Data contract class that provides constant data related to [ConceptDatabase] & it's related models.
 *
 * @author Nirav Joshi
 * @since 10/14/2019
 * @version 1.0.0
 */
class ConceptDataContract {
    companion object {
        // Tag for logcat.
        const val TAG = "ConceptDataContract"
        /**
         * defines database version for [ConceptDatabase]
         */
        const val DB_VERSION = 1
        /**
         * defines new database version for [ConceptDatabase]
         */
        const val NEW_DB_VERSION = 1
        /**
         * defines database name for [ConceptDatabase]
         */
        const val DB_NAME = BuildConfig.DATABASE_NAME
        /**
         * defines Association table name for [AssociationDto]
         */
        const val FEED_DETAIL_TABLE_NAME = "Feed_Detail"
        /**
         * defines column name '_id' for [FeedDetailDTO]
         */
        const val FEED_ID = "_id"

        /**
         * defines column name 'title' for [FeedDetailDTO]
         */
        const val FEED_TITLE = "title"

        /**
         * defines column name 'description' for [FeedDetailDTO]
         */
        const val FEED_DESCRIPTION = "description"

        /**
         * defines column name 'imageurl' for [FeedDetailDTO]
         */
        const val FEED_URL = "imageurl"

    }
}
