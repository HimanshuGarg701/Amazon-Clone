package com.example.amazonn

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReviewDao {

    @Insert
    fun insert(review : Review)

    @Delete
    fun delete(review : Review)

    @Query("SELECT * FROM review WHERE product_id =:key")
    fun getAllReviews(key : Int) : LiveData<List<Review>>

    @Query("DELETE FROM review")
    fun deleteAll()

    @Query("Select * FROM review WHERE heading =:key")
    fun getReview(key : String) : Review?

}