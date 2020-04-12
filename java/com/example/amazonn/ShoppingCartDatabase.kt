package com.example.amazonn

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 19, exportSchema = false)
abstract class ShoppingCartDatabase : RoomDatabase() {

    abstract val cartProductDao : CartProductDao

    companion object{

        @Volatile
        private var INSTANCE : ShoppingCartDatabase? = null

        fun getInstance(context : Context) : ShoppingCartDatabase{
            Log.d("EnteredDatabase", "ENTRY POINT")
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    Log.d("EnteredDatabase", "Intermediate stage")
                    instance = Room.databaseBuilder(context.applicationContext,
                                        ShoppingCartDatabase::class.java,
                                        "products_cart")
                                        .fallbackToDestructiveMigration()
                                        .build()
                    INSTANCE = instance
                }
                Log.d("EnteredDatabase", "EXIT POINT")
                return instance
            }
        }
    }
}