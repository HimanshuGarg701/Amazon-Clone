package com.example.amazonn

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 27, exportSchema = false)
abstract class ShoppingCartDatabase : RoomDatabase() {

    abstract val cartProductDao : CartProductDao

    companion object{

        @Volatile
        private var INSTANCE : ShoppingCartDatabase? = null

        fun getInstance(context : Context) : ShoppingCartDatabase {
            synchronized(this){
                var instance =
                    INSTANCE
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