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

    @Query("SELECT * FROM review")
    fun getAllProducts() : LiveData<List<Review>>

    @Query("DELETE FROM review")
    fun deleteAll()

    @Query("SELECT * FROM review WHERE id = :key")
    fun getProduct(key : Int) : Review?
}