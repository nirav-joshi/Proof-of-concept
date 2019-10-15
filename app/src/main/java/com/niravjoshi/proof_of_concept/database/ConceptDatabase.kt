package com.niravjoshi.proof_of_concept.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.niravjoshi.proof_of_concept.application.ProofApplication
import com.niravjoshi.proof_of_concept.concepts.data.FeedDao
import com.niravjoshi.proof_of_concept.concepts.model.FeedDetailDTO
import com.niravjoshi.proof_of_concept.concepts.model.FeedsDTO

/**
 * [ConceptDatabase] : Database class defined to provide and store data using [FeedDao] for [RoomDatabase] as ORM help for this project.
 *
 * @author Nirav Joshi
 * @since 10/14/2019
 * @version 1.0.0
 */
@Database(
        entities = [FeedDetailDTO::class],
        version = ConceptDataContract.DB_VERSION
)

abstract class ConceptDatabase : RoomDatabase() {
    private object Holder {
        val INSTANCE = ProofApplication.context.applicationContext?.let {
            Room.databaseBuilder(
                    it,
                    ConceptDatabase::class.java,
                ConceptDataContract.DB_NAME
            )
                    .fallbackToDestructiveMigration()
                    .build()

        }
    }

    companion object {
        // Tag for logcat.
        const val TAG = "ConceptDatabase"

        /**
         * Singleton instance of [ConceptDatabase]
         */
        val instance: ConceptDatabase? by lazy { Holder.INSTANCE }

    }

    abstract val feedDao: FeedDao

}