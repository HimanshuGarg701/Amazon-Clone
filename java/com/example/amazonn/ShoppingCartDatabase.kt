package com.example.amazonn

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ShoppingCartDatabase : RoomDatabase() {

    abstract val cartProductDao : CartProductDao

    companion object{

        @Volatile
        private var INSTANCE : ShoppingCartDatabase? = null

        @InternalCoroutinesApi
        fun getInstance(context : Context) : ShoppingCartDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(context.applicationContext,
                                        ShoppingCartDatabase::class.java,
                                        "products_cart")
                                        .fallbackToDestructiveMigration()
                                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}