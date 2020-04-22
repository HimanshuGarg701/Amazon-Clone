package com.example.amazonn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.amazonn.databinding.ActivityShoppingCartBinding
import kotlinx.android.synthetic.main.activity_shopping_cart.*

class ShoppingCart : AppCompatActivity() {

    var products = ArrayList<Product>()
    var total = ArrayList<Double>()
    private lateinit var binding : ActivityShoppingCartBinding
    lateinit var cartDao : CartProductDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_shopping_cart
        )
        binding.lifecycleOwner = this


        loadData()
    }


     private fun loadData(){
        val application = requireNotNull(this).application
        try {
            cartDao = ShoppingCartDatabase.getInstance(
                application
            ).cartProductDao
        }catch(e : Exception){
            Log.d("CreatingDao", e.message!!)
        }

        val cartViewModelFactory =
            CartViewModelFactory(
                cartDao,
                application
            )
        val cartViewModel = ViewModelProviders.of(this, cartViewModelFactory).get(
            ShoppingCartViewModel::class.java)

        cartViewModel.products.observe(this, Observer {
            products = it as ArrayList<Product>
            val adapter =
                ShoppingCartAdapter(
                    products
                )
            recyclerShoppingCart.adapter = adapter
        })

         cartViewModel.total?.observe(this, Observer {
             total = it as ArrayList<Double>
             var totalCost = 0.0
             for(i in total){
                 totalCost += i
             }

             totalPrice.text = "\$ ${totalCost}"
         })
    }

}
