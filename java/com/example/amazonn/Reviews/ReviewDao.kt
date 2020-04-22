
package com.example.amazonn.Reviews

import androidx.room.*
import com.example.amazonn.Reviews.Review

@Dao
interface ReviewDao {

    @Insert
    fun insert(review : Review)

    @Delete
    fun delete(review : Review)

    @Update
    fun updateReview(review : Review)

    @Query("SELECT review_data FROM review WHERE productId =:key")
    fun getAllReviews(key : Int) : String?

    @Query("DELETE FROM review")
    fun deleteAll()

}