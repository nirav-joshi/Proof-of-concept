package com.niravjoshi.proof_of_concept.concepts.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.niravjoshi.proof_of_concept.database.ConceptDataContract

/**
 * [FeedDetailDTO] : Database class defined to provide and store data using [FeedDetailDTO] for [RoomDatabase] as ORM help for this project.
 *
 * @author Nirav Joshi
 * @since 10/15/2019
 * @version 1.0.0
 */

@Entity(
        tableName = ConceptDataContract.FEED_DETAIL_TABLE_NAME
)
class FeedDetailDTO(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = ConceptDataContract.FEED_ID)
        @SerializedName("id")
        var mId: Long ,

        @ColumnInfo(name = ConceptDataContract.FEED_TITLE)
        @SerializedName("title")
        var mTitle: String?,

        @ColumnInfo(name = ConceptDataContract.FEED_DESCRIPTION)
        @SerializedName("description")
        var mDescription: String?,

        @ColumnInfo(name = ConceptDataContract.FEED_URL)
        @SerializedName("imageHref")
        var mImageUrl: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(mId)
        parcel.writeString(mTitle)
        parcel.writeString(mDescription)
        parcel.writeString(mImageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeedDetailDTO> {
        override fun createFromParcel(parcel: Parcel): FeedDetailDTO {
            return FeedDetailDTO(parcel)
        }

        override fun newArray(size: Int): Array<FeedDetailDTO?> {
            return arrayOfNulls(size)
        }
    }
}



