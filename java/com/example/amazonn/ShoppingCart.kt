package com.example.amazonn

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.amazonn.databinding.ActivityShoppingCartBinding
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.coroutines.*

class ShoppingCart : AppCompatActivity() {

    private lateinit var binding : ActivityShoppingCartBinding
    private lateinit var cartDao : CartProductDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)
        binding.lifecycleOwner = this

        Log.d("CreatingDao", "Before Creation")
        try {
            cartDao = ProductDescription().getDao()
        }catch(e : Exception){
            Log.d("CreatingDao", e.message!!)
        }
        Log.d("CreatingDao", "After Creation")

        val products = cartDao.getAllProducts()
        var result = ArrayList<Product>()

        products?.observe(this, Observer{
            result = it as ArrayList<Product>
        })

        val adapter : ShoppingCartAdapter = try {
            ShoppingCartAdapter(result)
        } catch(e: Exception){
            Log.d("ShoppingCartError", "Failed to load data")
            ShoppingCartAdapter(ArrayList<Product>())
        }
        recyclerShoppingCart.adapter = adapter
    }



}
