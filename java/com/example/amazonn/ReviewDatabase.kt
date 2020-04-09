package com.example.amazonn

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Review::class], version = 4, exportSchema = false)
abstract class ReviewDatabase : RoomDatabase(){
    abstract val reviewDao : ReviewDao

    companion object{

        @Volatile
        private var INSTANCE : ReviewDatabase? = null

        fun getInstance(context : Context) : ReviewDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        ReviewDatabase::class.java,
                        "reviews")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
