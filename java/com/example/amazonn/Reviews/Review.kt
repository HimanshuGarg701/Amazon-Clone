package com.example.amazonn.Reviews

import android.os.Parcel
import android.os.Parcelable

data class Review(
    val productId : Int,

    var reviewData : String?) : Parcelable{

    constructor() : this(0, "")

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(productId)
        parcel.writeString(reviewData)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Review> {
        override fun createFromParcel(parcel: Parcel): Review {
            return Review(parcel)
        }

        override fun newArray(size: Int): Array<Review?> {
            return arrayOfNulls(size)
        }
    }
}