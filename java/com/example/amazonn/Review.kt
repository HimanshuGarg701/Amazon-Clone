package com.example.amazonn

import android.os.Parcel
import android.os.Parcelable

data class Review(
    val productId : Int,
    val reviewId : String?,
    var reviewHeading : String?,
    var reviewData : String?) : Parcelable{

    constructor() : this(0,"", "", "")

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(productId)
        parcel.writeString(reviewId)
        parcel.writeString(reviewHeading)
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