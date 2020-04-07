package com.example.amazonn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_shopping_cart.*

class ShoppingCart : AppCompatActivity() {

    private val products = ArrayList<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        try {
            val product = intent.getParcelableExtra<Product>("PRODUCT")
            products.add(product!!)
        }catch(e : Exception){
            // Do Nothing
        }
        val adapter = ShoppingCartAdapter(products)
        recyclerShoppingCart.adapter = adapter
    }
}
