package com.niravjoshi.proof_of_concept.concepts.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * FeedsDTO :
 *
 *
 * @author : Nirav Joshi
 * @version 1.0.0
 * @since 10/14/2019
 */

class FeedsDTO() :Parcelable {
    @SerializedName("title")
    var mFeedTitle: String? = null
    @SerializedName("rows")
    var mFeedsList: List<FeedDetailDTO>? = null

    constructor(parcel: Parcel) : this() {
        mFeedTitle = parcel.readString()
        mFeedsList = parcel.createTypedArrayList(FeedDetailDTO)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mFeedTitle)
        parcel.writeTypedList(mFeedsList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeedsDTO> {
        override fun createFromParcel(parcel: Parcel): FeedsDTO {
            return FeedsDTO(parcel)
        }

        override fun newArray(size: Int): Array<FeedsDTO?> {
            return arrayOfNulls(size)
        }
    }


}