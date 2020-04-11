package com.example.amazonn

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.collections.ArrayList

@Entity(tableName="product")
data class Product(
    @PrimaryKey
    val id : Int,

    @ColumnInfo(name="product_name")
    val name: String?,

    @ColumnInfo(name="product_price")
    val price:String?,

    @ColumnInfo(name="product_quantity")
    val quantity : String?,

    @ColumnInfo(name="product_image")
    val imageURL : String?,

    @ColumnInfo(name="product_description")
    val description:String?,

    @ColumnInfo(name="product_reviews")
    val reviewsOfProduct : ArrayList<Review>? = ArrayList<Review>()
    ) : Parcelable
{

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        readArrayList(ArrayList<Review>()) as ArrayList<Review>?
    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(quantity)
        parcel.writeString(imageURL)
        parcel.writeString(description)
        parcel.writeList(reviewsOfProduct as List<*>?)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}

private fun readArrayList(arrayList: ArrayList<Review>): ArrayList<*>? {
    return arrayList
}

