package com.example.amazonn

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class TypeConvertor {


    fun stringToObject(data : String) : ArrayList<String>?{
         val gson = Gson()
        if(data==null){
            return null
        }

        val listType: Type =
            object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    fun objectToString(review : String) : String{
        val gson = Gson()
        return gson.toJson(review)
    }

    fun listObjectToString(review : ArrayList<String>) : String{
        val gson = Gson()
        return gson.toJson(review)
    }
}