package com.example.amazonn

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class TypeConvertor {
    private var gson = Gson()

    fun stringToObject(data : String) : List<Product>?{
        if(data==null){
            return null
        }

        val listType: Type =
            object : TypeToken<List<Product?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    fun objectToString(products : List<Product>) : String{
        return gson.toJson(products)
    }
}