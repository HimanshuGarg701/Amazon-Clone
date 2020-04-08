package com.example.amazonn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.amazonn.databinding.ActivityShoppingCartBinding
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.coroutines.*

class ShoppingCart : AppCompatActivity() {

    private val job = Job()
    val scope = CoroutineScope(Dispatchers.Main + job)

    private lateinit var binding : ActivityShoppingCartBinding
    private lateinit var cartDao : CartProductDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart)
        binding.lifecycleOwner = this

        Log.d("CreatingDao", "Before Creation")
        try {
            cartDao = ShoppingCartDatabase.getInstance(requireNotNull(this).application).cartProductDao
            Log.d("CreatingDao", "Inside try ${cartDao.toString()}")
        }catch(e : Exception){
            Log.d("CreatingDao", "Error It Is")
            Log.d("CreatingDao", "This is ${cartDao.toString()}")
            Log.d("CreatingDao", e.message!!)
        }
        var products = ArrayList<Product>()

        scope.launch {
            withContext(Dispatchers.IO){
                try {
                    products = cartDao.getAllProducts() as ArrayList<Product>
                    val adapter = ShoppingCartAdapter(products)
                    recyclerShoppingCart.adapter = adapter
                    Log.d("CreatingDao", "Failed in the background thread ${products.toString()}")
                }catch(e : Exception){
                    Log.d("CreatingDao", "Failed to fetch ${e.message}")
                }
            }
        }
    }



}
