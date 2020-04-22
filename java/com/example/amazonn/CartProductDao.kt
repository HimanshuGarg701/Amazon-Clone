package com.example.amazonn

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.amazonn.Product

@Dao
interface CartProductDao {

    @Insert
    fun insert(product : Product)

    @Delete
    fun delete(product : Product)

    @Query("SELECT * FROM product")
    fun getAllProducts() : LiveData<List<Product>>

    @Query("DELETE FROM product")
    fun deleteAll()

    @Query("SELECT * FROM product WHERE id = :key")
    fun getProduct(key : Int) : Product?

    @Query("SELECT product_price from product")
    fun getPrice() : LiveData<List<Double>>?
}