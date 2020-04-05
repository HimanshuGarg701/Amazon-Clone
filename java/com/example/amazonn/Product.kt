package com.example.amazonn

data class Product(val name: String, val price:String, val quantity : String, val imageURL : String, val description:String) {

    override fun toString(): String {
        return ("name is : $name , and price is $price")
    }
}