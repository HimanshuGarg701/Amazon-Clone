package com.example.amazonn

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartProductDao {

    @Insert
    fun insert(product : Product)

    @Delete
    fun delete(product : Product)

    @Query("DELETE FROM product")
    fun deleteAll()

    @Query("SELECT * FROM product ORDER BY id")
    fun getAllProducts()
}