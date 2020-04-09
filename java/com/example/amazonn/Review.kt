package com.example.amazonn

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class Review(

    @PrimaryKey(autoGenerate = true)
    val id : Int,

    @ColumnInfo(name="review_heading")
    val heading: String,

    @ColumnInfo(name="review_data")
    val reviewData : String) {
}